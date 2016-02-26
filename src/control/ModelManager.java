package control;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

	public ModelManager(JModelManagement frame, JFrame parent) {
		super(frame, parent);
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
		showThingOnPanel(currentThing, currentClass);
	}

	private void showThingOnPanel(AThing thing, Class clazz) {
		if (null == thing || null == clazz) {
			return;
		}
		// clean all
		panelEdition.removeAll();
		// reflection of class elements
		Field[] fields = currentClass.getFields();
		JComponent[] fieldValues = new JComponent[fields.length];
		panelEdition.setLayout(new GridLayout(fields.length, 2, 4, 2));
		// the relationship between the property and the input region should be
		// kept.
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			// property name.
			panelEdition.add(new JLabel(field.getName() + ": "));
			//System.out.println(field.getName());
			// property value.
			if (AThing.class.isAssignableFrom(field.getType())) {
				try {
					Object property = field.get(currentClass.cast(currentThing));
					if (null == property) {
						fieldValues[i] = new JTextField("NULL");
					} else {
						fieldValues[i] = new JTextField(property.toString());
					}
					panelEdition.add(fieldValues[i]);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			} else if ((field.getType().isPrimitive() && !field.getType().equals(boolean.class))
					|| field.getType().equals(String.class)) {
				try {
					Object property = field.get(currentClass.cast(currentThing));
					if (null == property) {
						fieldValues[i] = new JTextField("NULL");
					} else {
						fieldValues[i] = new JTextField(property.toString());
					}
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
				panelEdition.add(fieldValues[i]);
			} else if (field.getType().equals(boolean.class)) {// boolean
				JRadioButton optTrue = new JRadioButton("true");
				JRadioButton optFalse = new JRadioButton("false");
				ButtonGroup btnGtf = new ButtonGroup();
				btnGtf.add(optTrue);
				btnGtf.add(optFalse);
				JPanel pnlTF = new JPanel();
				pnlTF.setLayout(new FlowLayout(FlowLayout.LEFT));
				pnlTF.add(optTrue);
				pnlTF.add(optFalse);
				try {
					if (((Boolean) field.get(currentThing)).booleanValue()) {
						optTrue.setSelected(true);
					} else {
						optFalse.setSelected(true);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				panelEdition.add(pnlTF);
				fieldValues[i] = optTrue;
			} else if (Set.class.isAssignableFrom(field.getType())) { // a set
				ParameterizedType elementType = (ParameterizedType) field.getGenericType();
				Class<?> actualType = (Class<?>) elementType.getActualTypeArguments()[0];
				try {
					Set<? extends Object> elements = (Set<? extends Object>)field.get(currentClass.cast(currentThing));
					if (null == elements) {
						fieldValues[i] = new JTextField("Empty Set of " + actualType.getSimpleName());
					} else {
						JList lElements = new JList();
						DefaultListModel listElementsModel = new DefaultListModel();
						elements.forEach(e -> listElementsModel.addElement(e));
						lElements.setModel(listElementsModel);
						fieldValues[i] = lElements;//new JTextField(elements.toString());
					}
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
				//fieldValues[i] = new JTextField("Set of " + actualType.getSimpleName());
				panelEdition.add(fieldValues[i]);
			} else if (false) {// field.isEnumConstant()){// enum
				try {
					fieldValues[i] = new JTextField(field.get(currentClass.cast(currentThing)).toString());
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
				panelEdition.add(fieldValues[i]);
			} else {
				// fieldValues[i] = new
				// JTextField(field.getType().getSimpleName());
				// panelEdition.add(fieldValues[i]);
				try {
					Object property = field.get(currentClass.cast(currentThing));
					if (null == property) {
						fieldValues[i] = new JTextField("NULL");
					} else {
						fieldValues[i] = new JTextField(property.toString());
					}
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
				panelEdition.add(fieldValues[i]);
			}

		}

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

	public class NewItemDocumentChanged implements DocumentListener {

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
		if (0 < txt.length() && txt.matches(regex)) {
			btnAdd.setEnabled(true);
		} else {
			btnAdd.setEnabled(false);
		}
	}

	private void btnAddMouseReleased(MouseEvent e) {
		// TODO add your code here
		if (MouseEvent.BUTTON1 != e.getButton() || !btnAdd.isEnabled()) {
			return;
		}
		System.out.println("Add Button Released.");
	}

	private void btnDelMouseReleased(MouseEvent e) {
		// TODO add your code here
		if (MouseEvent.BUTTON1 != e.getButton() || !btnDel.isEnabled()) {
			return;
		}
		System.out.println("Del Button Released.");
	}

	public void setConcepts() {
		cmbBoxConcepts.removeAllItems();
		cmbBoxConcepts.addItem("Scenario");
		cmbBoxConcepts.addItem("MicroSequence");
		cmbBoxConcepts.addItem("DialogueSession");
		cmbBoxConcepts.addItem("Pair");
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
		// txtNewItem.addPropertyChangeListener("text", e ->
		// txtNewItemPropertyChange(e));
		txtNewItem.getDocument().addDocumentListener(new NewItemDocumentChanged());
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				btnAddMouseReleased(e);
			}
		});
		btnDel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				btnDelMouseReleased(e);
			}
		});
	}

	@Override
	public void initilizeControl() {
		setConcepts();
		setLstItems();
		// --end
		bindedFrame.setVisible(true);
	}

	@Override
	void thisWindowClosed(WindowEvent e) {
		if (null != this.parent){
			this.parent.setVisible(true);
		}		
	}

}
