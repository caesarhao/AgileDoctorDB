package control;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import jpa.JpaManager;
import model.AThing;
import view.*;

public class ModelManager extends AJFrameControl<JModelManagement>{

	private JComboBox cmbBoxConcepts = null;
	private JList lstItems = null;
	private DefaultListModel listModel = null;
	private JPanel panelEdition = null;
	private Class currentClass = null;
	
	public ModelManager(JModelManagement frame) {
		super(frame);
		cmbBoxConcepts = bindedFrame.getCmbBoxConcepts();
		lstItems = bindedFrame.getLstItems();
		listModel = new DefaultListModel();
		panelEdition = bindedFrame.getPanelEdition();
		try {
			currentClass = Class.forName("model.Scenario");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void lstItemsValueChanged(ListSelectionEvent e) {
		JList list = (JList) e.getSource();
		String selectedItemName = (String)list.getSelectedValue();
		//JOptionPane.showMessageDialog(null, selectedItemName);
		//clean all
		panelEdition.removeAll();
		// reflection of class elements
		Field[] fields = currentClass.getFields();
		for (int i = 0; i < fields.length; i++){
			Field field = fields[i];
			panelEdition.add(new JLabel(field.getName()+": "));
			if(String.class == field.getType()){
				panelEdition.add(new JTextField());
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
			currentClass = Class.forName("model."+selectedItem);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
        List<AThing> individuals = JpaManager.<AThing>findAll(selectedItem);
        listModel.removeAllElements();
        individuals.forEach(i -> listModel.addElement(i.getName()));
        lstItems.setModel(listModel);
	}
	
	public void setConcepts(){
		cmbBoxConcepts.removeAllItems();
		cmbBoxConcepts.addItem("Scenario");
		cmbBoxConcepts.addItem("MicroSequence");
		cmbBoxConcepts.addItem("DialogueSession");
		cmbBoxConcepts.addItem("Phrase");
		cmbBoxConcepts.addItem("DoctorActor");
		cmbBoxConcepts.addItem("PatientActor");
		cmbBoxConcepts.addItem("MedicalInformation");
		cmbBoxConcepts.addItem("FamilyInformation");
		cmbBoxConcepts.addActionListener(e -> cmbBoxConceptsActionPerformed(e));
	}
	public void setLstItems(){
		lstItems.addListSelectionListener(e -> lstItemsValueChanged(e));
	}
	
	@Override
	void initilizeControl() {
		setConcepts();
		setLstItems();
		//--end
		bindedFrame.setVisible(true);
	}
	

}
