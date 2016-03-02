/*
 * Created by JFormDesigner on Fri Oct 30 00:58:29 CET 2015
 */

package view;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * @author Franck Duc
 */
public class JModelManagement extends JFrame {
	public JModelManagement() {
		initComponents();
	}
	public JModelManagement(String title){
		this();
		this.setTitle(title);
	}

	private void cmbBoxConceptsItemStateChanged(ItemEvent e) {
		// TODO add your code here
	}

	private void lstItemsValueChanged(ListSelectionEvent e) {
		// TODO add your code here
	}

	private void saveButtonMouseReleased(MouseEvent e) {
		// TODO add your code here
	}

	private void cancelButtonMouseReleased(MouseEvent e) {
		// TODO add your code here
	}

	public JComboBox getCmbBoxConcepts() {
		return cmbBoxConcepts;
	}

	public JList getLstItems() {
		return lstItems;
	}

	public JScrollPane getScrollPaneEditRegion() {
		return scrollPaneEditRegion;
	}

	private void cmbBoxConceptsActionPerformed(ActionEvent e) {
		
	}

	private void createUIComponents() {
		// TODO: add custom component creation code here
	}

	public JPanel getPanelEdition() {
		return panelEdition;
	}

	public JButton getBtnDel() {
		return btnDel;
	}

	public JTextField getTxtNewItem() {
		return txtNewItem;
	}

	public JButton getBtnAdd() {
		return btnAdd;
	}

	private void txtNewItemPropertyChange(PropertyChangeEvent e) {
		// TODO add your code here
	}

	private void btnAddMouseReleased(MouseEvent e) {
		// TODO add your code here
	}

	private void btnDelMouseReleased(MouseEvent e) {
		// TODO add your code here
	}

	public JPanel getPanelEditionLeft() {
		return panelEditionLeft;
	}

	public JPanel getPanelEditionRight() {
		return panelEditionRight;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		label3 = new JLabel();
		cmbBoxConcepts = new JComboBox();
		label4 = new JLabel();
		scrollPane3 = new JScrollPane();
		lstItems = new JList();
		scrollPaneEditRegion = new JScrollPane();
		panelEdition = new JPanel();
		panelEditionLeft = new JPanel();
		panelEditionRight = new JPanel();
		buttonBar = new JPanel();
		panel1 = new JPanel();
		btnDel = new JButton();
		txtNewItem = new JTextField();
		btnAdd = new JButton();
		saveButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("AgileDoctorDB Model Management");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridLayout());

				//======== panel4 ========
				{
					panel4.setLayout(new GridBagLayout());
					((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {200, 600, 0};
					((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 385, 0};
					((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
					((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

					//======== panel5 ========
					{
						panel5.setLayout(new FlowLayout(FlowLayout.LEFT));

						//---- label3 ----
						label3.setText("Concept: ");
						panel5.add(label3);
						panel5.add(cmbBoxConcepts);
					}
					panel4.add(panel5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- label4 ----
					label4.setText("Edition Region:");
					panel4.add(label4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== scrollPane3 ========
					{

						//---- lstItems ----
						lstItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						scrollPane3.setViewportView(lstItems);
					}
					panel4.add(scrollPane3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));

					//======== scrollPaneEditRegion ========
					{

						//======== panelEdition ========
						{
							panelEdition.setLayout(new GridBagLayout());
							((GridBagLayout)panelEdition.getLayout()).columnWidths = new int[] {163, 410, 0};
							((GridBagLayout)panelEdition.getLayout()).rowHeights = new int[] {0, 0};
							((GridBagLayout)panelEdition.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
							((GridBagLayout)panelEdition.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

							//======== panelEditionLeft ========
							{
								panelEditionLeft.setLayout(new GridLayout(8, 1, 4, 2));
							}
							panelEdition.add(panelEditionLeft, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));

							//======== panelEditionRight ========
							{
								panelEditionRight.setLayout(new GridLayout(8, 1, 4, 2));
							}
							panelEdition.add(panelEditionRight, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						}
						scrollPaneEditRegion.setViewportView(panelEdition);
					}
					panel4.add(scrollPaneEditRegion, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				contentPanel.add(panel4);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//======== panel1 ========
				{
					panel1.setLayout(new FlowLayout(FlowLayout.LEFT));

					//---- btnDel ----
					btnDel.setText("Delete");
					btnDel.setEnabled(false);
					panel1.add(btnDel);

					//---- txtNewItem ----
					txtNewItem.setColumns(10);
					panel1.add(txtNewItem);

					//---- btnAdd ----
					btnAdd.setText("Add");
					btnAdd.setEnabled(false);
					panel1.add(btnAdd);
				}
				buttonBar.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- saveButton ----
				saveButton.setText("Save");
				saveButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						saveButtonMouseReleased(e);
					}
				});
				buttonBar.add(saveButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						cancelButtonMouseReleased(e);
					}
				});
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
	private JPanel panel4;
	private JPanel panel5;
	private JLabel label3;
	private JComboBox cmbBoxConcepts;
	private JLabel label4;
	private JScrollPane scrollPane3;
	private JList lstItems;
	private JScrollPane scrollPaneEditRegion;
	private JPanel panelEdition;
	private JPanel panelEditionLeft;
	private JPanel panelEditionRight;
	private JPanel buttonBar;
	private JPanel panel1;
	private JButton btnDel;
	private JTextField txtNewItem;
	private JButton btnAdd;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
