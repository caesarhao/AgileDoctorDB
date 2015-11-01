/*
 * Created by JFormDesigner on Fri Oct 30 00:58:29 CET 2015
 */

package view;

import java.awt.*;
import java.awt.event.*;
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

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Franck Duc
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
		buttonBar = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

			// JFormDesigner evaluation mark
			dialogPane.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

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

						//---- cmbBoxConcepts ----
						cmbBoxConcepts.addItemListener(e -> cmbBoxConceptsItemStateChanged(e));
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
						lstItems.addListSelectionListener(e -> lstItemsValueChanged(e));
						scrollPane3.setViewportView(lstItems);
					}
					panel4.add(scrollPane3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 5), 0, 0));
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
	// Generated using JFormDesigner Evaluation license - Franck Duc
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
	private JPanel buttonBar;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
