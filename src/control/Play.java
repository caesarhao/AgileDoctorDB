package control;

import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import jpa.JpaManager;
import model.AThing;
import model.*;
import view.JPlay;

public class Play extends AJFrameControl<JPlay> {
	private JComboBox cmbBoxDoctor = null;
	private JComboBox cmbBoxPatient = null;
	private JCheckBox chkBoxDoctorAI = null;
	private JCheckBox chkBoxPatientAI = null;
	private JComboBox cmbBoxDoctorPhrase = null;
	private JComboBox cmbBoxPatientPhrase = null;
	
	private JLabel lblDoctor = null;
	private JLabel lblPatient = null;
	private JTextArea txtAreaLog = null;
	
	String namedQuery = null;
	Map<String, Object> queryParams = null;
	List<AThing> queryResult = null;
	
	private DoctorActor da = null;
	private PatientActor pa = null;
	
	public Play(JPlay frame, JFrame parent) {
		super(frame, parent);
		cmbBoxDoctor = bindedFrame.getCmbBoxDoctor();
		cmbBoxPatient = bindedFrame.getCmbBoxPatient();
		chkBoxDoctorAI = bindedFrame.getChkBoxDoctorAI();
		chkBoxPatientAI = bindedFrame.getChkBoxPatientAI();
		cmbBoxDoctorPhrase = bindedFrame.getCmbBoxDoctorPhrase();
		cmbBoxPatientPhrase = bindedFrame.getCmbBoxPatientPhrase();
		lblDoctor = bindedFrame.getLblDoctor();
		lblPatient = bindedFrame.getLblPatient();
		txtAreaLog = bindedFrame.getTxtAreaLog();
		
		queryParams = new HashMap<String, Object>();
		
		cmbBoxDoctor.addItem("");
		namedQuery = "DoctorActor.findAll";
		queryResult = JpaManager.<AThing> findWithNamedQuery(namedQuery, queryParams);
		if (!queryResult.isEmpty()) {
			queryResult.forEach(e -> cmbBoxDoctor.addItem(e.getName()));
		}
		cmbBoxDoctor.addItemListener(e -> cmbBoxDoctorItemStateChanged(e));
		
		cmbBoxPatient.addItem("");
		namedQuery = "PatientActor.findAll";
		queryResult = JpaManager.<AThing> findWithNamedQuery(namedQuery, queryParams);
		if (!queryResult.isEmpty()) {
			queryResult.forEach(e -> cmbBoxPatient.addItem(e.getName()));
		}
		cmbBoxPatient.addItemListener(e -> cmbBoxPatientItemStateChanged(e));
	}

	private void cmbBoxDoctorItemStateChanged(ItemEvent e) {
		String itemName = e.getItem().toString();
		if (itemName.equals("")){
			da = null;
			lblDoctor.setIcon(null);
		}
		else{
			namedQuery = "DoctorActor.findByName";
			queryParams.clear();
			queryParams.put("name", itemName);
			queryResult = JpaManager.<AThing> findWithNamedQuery(namedQuery, queryParams);
			if (!queryResult.isEmpty()) {
				da = (DoctorActor)queryResult.get(0);
				if (da.sex){
					lblDoctor.setIcon(new ImageIcon(((new ImageIcon("resources/maledoctor.jpg")).getImage()).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
				}
				else{
					lblDoctor.setIcon(new ImageIcon(((new ImageIcon("resources/femaledoctor.jpg")).getImage()).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
				}
			}
		}
	}
	
	private void cmbBoxPatientItemStateChanged(ItemEvent e) {
		String itemName = e.getItem().toString();
		if (itemName.equals("")){
			pa = null;
			lblPatient.setIcon(null);
		}
		else{
			namedQuery = "PatientActor.findByName";
			queryParams.clear();
			queryParams.put("name", itemName);
			queryResult = JpaManager.<AThing> findWithNamedQuery(namedQuery, queryParams);
			if (!queryResult.isEmpty()) {
				pa = (PatientActor)queryResult.get(0);
				if (pa.sex){
					lblPatient.setIcon(new ImageIcon(((new ImageIcon("resources/malepatient.jpg")).getImage()).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
				}
				else{
					lblPatient.setIcon(new ImageIcon(((new ImageIcon("resources/femalepatient.jpg")).getImage()).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
				}
			}
		}
	}

	@Override
	public void initilizeControl() {
		bindedFrame.setVisible(true);
	}
	
	@Override
	void thisWindowClosed(WindowEvent e) {
		if (null != this.parent){
			this.parent.setVisible(true);
		}		
	}

}
