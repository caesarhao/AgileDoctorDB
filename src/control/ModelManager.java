package control;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;

import jpa.JpaManager;
import model.AThing;
import view.*;

public class ModelManager extends AJFrameControl<JModelManagement> {

	private JComboBox cmbBoxConcepts = null;
	private JList lstItems = null;
	private DefaultListModel listModel = null;
	private JPanel panelEdition = null;
	private Class currentClass = null;
	private AThing currentThing = null;
	private JTextField txtNewItem = null;
	private JButton btnAdd = null;
	private JButton btnDel = null;

	public ModelManager(JModelManagement frame) {
		super(frame);
		cmbBoxConcepts = bindedFrame.getCmbBoxConcepts();
		lstItems = bindedFrame.getLstItems();
		listModel = new DefaultListModel();
		panelEdition = bindedFrame.getPanelEdition();
		txtNewItem = bindedFrame.getTxtNewItem();
		btnAdd = bindedFrame.getBtnAdd();
		btnDel = bindedFrame.getBtnDel();
		try {
			currentClass = Class.forName("model.Scenario");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void lstItemsValueChanged(ListSelectionEvent e) {
		JList list = (JList) e.getSource();
		String selectedItemName = (String) list.getSelectedValue();
		// get current Thing object.
		String namedQuery = currentClass.getSimpleName() + ".findByName";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", selectedItemName);
		List<AThing> queryResult = JpaManager.<AThing> findWithNamedQuery(namedQuery, queryParams);
		if (!queryResult.isEmpty()) {
			currentThing = queryResult.get(0);
			btnDel.setEnabled(true);
		} else {
			btnDel.setEnabled(false);
			return;
		}
		// JOptionPane.showMessageDialog(null, selectedItemName);
		// clean all
		panelEdition.removeAll();

		// reflection of class elements
		Field[] fields = currentClass.getFields();
		panelEdition.setLayout(new GridLayout(fields.length, 2, 4, 2));
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			panelEdition.add(new JLabel(field.getName() + ": "));
			if (field.getType().isPrimitive() || String.class == field.getType() || AThing.class.isAssignableFrom(field.getType())) {
				// System.out.println(currentThing.name);
				try {
					panelEdition.add(new JTextField(field.get(currentClass.cast(currentThing)).toString()));
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				panelEdition.add(new JTextField("TODO"));
			}

		}
		// add name set

		// refresh the display
		panelEdition.revalidate();
		panelEdition.repaint();
	}

	private void cmbBoxConceptsActionPerformed(ActionEvent e) {
		JComboBox<String> combo = (JComboBox<String>) e.getSource();
		String selectedItem = (String) combo.getSelectedItem();
		try {
			currentClass = Class.forName("model." + selectedItem);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		List<AThing> individuals = JpaManager.<AThing> findAll(selectedItem);
		listModel.removeAllElements();
		individuals.forEach(i -> listModel.addElement(i.getName()));
		lstItems.setModel(listModel);
	}
	
	public class NewItemDocumentChanged implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			txtNewItemPropertyChange(null);
			
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			txtNewItemPropertyChange(null);
			
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			txtNewItemPropertyChange(null);
			
		}
		
	}
	private void txtNewItemPropertyChange(PropertyChangeEvent e) {
		String txt = txtNewItem.getText();
		String regex = "([A-Z]|[a-z]|_)([A-Z]|[a-z]|[0-9]|_)*";
		if (0 < txt.length() && txt.matches(regex)){
			btnAdd.setEnabled(true);
		}
		else{
			btnAdd.setEnabled(false);
		}
	}

	public void setConcepts() {
		cmbBoxConcepts.removeAllItems();
		cmbBoxConcepts.addItem("Scenario");
		cmbBoxConcepts.addItem("MicroSequence");
		cmbBoxConcepts.addItem("DialogueSession");
		cmbBoxConcepts.addItem("DoctorPhrase");
		cmbBoxConcepts.addItem("PatientPhrase");
		cmbBoxConcepts.addItem("DoctorActor");
		cmbBoxConcepts.addItem("PatientActor");
		cmbBoxConcepts.addItem("MedicalInformation");
		cmbBoxConcepts.addItem("FamilyInformation");
		cmbBoxConcepts.addActionListener(e -> cmbBoxConceptsActionPerformed(e));
	}

	public void setLstItems() {
		lstItems.addListSelectionListener(e -> lstItemsValueChanged(e));
		//txtNewItem.addPropertyChangeListener("text", e -> txtNewItemPropertyChange(e));
		txtNewItem.getDocument().addDocumentListener(new NewItemDocumentChanged());
	}

	@Override
	void initilizeControl() {
		setConcepts();
		setLstItems();
		// --end
		bindedFrame.setVisible(true);
	}

}
