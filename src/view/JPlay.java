/*
 * Created by JFormDesigner on Sat Feb 13 16:28:09 CET 2016
 */

package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author DD
 */
public class JPlay extends JFrame {
	public JPlay() {
		initComponents();
	}
	public JPlay(String title){
		this();
		this.setTitle(title);
	}

	public JComboBox getCmbBoxDoctor() {
		return cmbBoxDoctor;
	}

	public JComboBox getCmbBoxPatient() {
		return cmbBoxPatient;
	}

	public JLabel getLblDoctor() {
		return lblDoctor;
	}

	public JLabel getLblPatient() {
		return lblPatient;
	}

	public JComboBox getCmbBoxDoctorPhrase() {
		return cmbBoxDoctorPhrase;
	}

	public JComboBox getCmbBoxPatientPhrase() {
		return cmbBoxPatientPhrase;
	}

	public JTextArea getTxtAreaLog() {
		return txtAreaLog;
	}

	private void cmbBoxDoctorItemStateChanged(ItemEvent e) {
		// TODO add your code here
	}

	private void thisWindowClosed(WindowEvent e) {
		// TODO add your code here
	}

	public JCheckBox getChkBoxDoctorAI() {
		return chkBoxDoctorAI;
	}

	public JCheckBox getChkBoxPatientAI() {
		return chkBoxPatientAI;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		label2 = new JLabel();
		cmbBoxDoctor = new JComboBox();
		chkBoxDoctorAI = new JCheckBox();
		panel4 = new JPanel();
		label3 = new JLabel();
		cmbBoxPatient = new JComboBox();
		chkBoxPatientAI = new JCheckBox();
		lblDoctor = new JLabel();
		lblPatient = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		cmbBoxDoctorPhrase = new JComboBox();
		cmbBoxPatientPhrase = new JComboBox();
		label1 = new JLabel();
		scrollPane1 = new JScrollPane();
		txtAreaLog = new JTextArea();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new FlowLayout());

				//======== panel1 ========
				{
					panel1.setLayout(new GridBagLayout());
					((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {149, 0, 132, 0};
					((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
					((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

					//======== panel2 ========
					{
						panel2.setLayout(new GridBagLayout());
						((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {255, 238, 0};
						((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 82, 0, 0, 0, 0};
						((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

						//======== panel3 ========
						{
							panel3.setLayout(new FlowLayout(FlowLayout.LEFT));

							//---- label2 ----
							label2.setText("Doctor: ");
							panel3.add(label2);
							panel3.add(cmbBoxDoctor);

							//---- chkBoxDoctorAI ----
							chkBoxDoctorAI.setText("AI mode");
							panel3.add(chkBoxDoctorAI);
						}
						panel2.add(panel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//======== panel4 ========
						{
							panel4.setLayout(new FlowLayout(FlowLayout.LEFT));

							//---- label3 ----
							label3.setText("Patient: ");
							panel4.add(label3);
							panel4.add(cmbBoxPatient);

							//---- chkBoxPatientAI ----
							chkBoxPatientAI.setText("AI mode");
							panel4.add(chkBoxPatientAI);
						}
						panel2.add(panel4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//---- lblDoctor ----
						lblDoctor.setIcon(null);
						panel2.add(lblDoctor, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- lblPatient ----
						lblPatient.setIcon(null);
						panel2.add(lblPatient, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//---- label4 ----
						label4.setText("Phrase: ");
						panel2.add(label4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- label5 ----
						label5.setText("Phrase: ");
						panel2.add(label5, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
						panel2.add(cmbBoxDoctorPhrase, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
						panel2.add(cmbBoxPatientPhrase, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
					}
					panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//---- label1 ----
					label1.setText("Log:");
					panel1.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== scrollPane1 ========
					{
						scrollPane1.setViewportView(txtAreaLog);
					}
					panel1.add(scrollPane1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(panel1);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JLabel label2;
	private JComboBox cmbBoxDoctor;
	private JCheckBox chkBoxDoctorAI;
	private JPanel panel4;
	private JLabel label3;
	private JComboBox cmbBoxPatient;
	private JCheckBox chkBoxPatientAI;
	private JLabel lblDoctor;
	private JLabel lblPatient;
	private JLabel label4;
	private JLabel label5;
	private JComboBox cmbBoxDoctorPhrase;
	private JComboBox cmbBoxPatientPhrase;
	private JLabel label1;
	private JScrollPane scrollPane1;
	private JTextArea txtAreaLog;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
