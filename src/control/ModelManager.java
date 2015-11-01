package control;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import jpa.JpaManager;
import model.Thing;
import view.*;

public class ModelManager extends AJFrameControl<JModelManagement>{

	private JComboBox cmbBoxConcepts = null;
	private JList lstItems = null;
	private DefaultListModel listModel = null;
	
	public ModelManager(JModelManagement frame) {
		super(frame);
		cmbBoxConcepts = bindedFrame.getCmbBoxConcepts();
		lstItems = bindedFrame.getLstItems();
		listModel = new DefaultListModel();
	}
	
	private void lstItemsValueChanged(ListSelectionEvent e) {
		JList list = (JList) e.getSource();
		String selectedItemName = (String)list.getSelectedValue();
		//JOptionPane.showMessageDialog(null, selectedItemName);
	}
	
	private void cmbBoxConceptsActionPerformed(ActionEvent e) {
		JComboBox<String> combo = (JComboBox<String>) e.getSource();
        String selectedItem = (String) combo.getSelectedItem();
        List<Thing> individuals = JpaManager.<Thing>findAll(selectedItem);
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
		cmbBoxConcepts.addItem("Symptom");
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
	
	void dealWithCmbBoxConceptsAction(Action e){
		
	}
}
