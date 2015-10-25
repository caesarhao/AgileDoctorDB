package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class JMain extends JFrame {

	private JPanel contentPane;
	private JMain self;
	private JComboBox cbPatient;
	

	/**
	 * Create the frame.
	 */
	public JMain(String name) {
		super(name);
		self = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnManager = new JButton(new ImageIcon(((new ImageIcon("resources/setting-icon.png")).getImage()).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));  
		btnManager.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//JManager jm = new JManager();
				self.setVisible(false);
				//jm.setVisible(true);
			}
		});
		contentPane.add(btnManager);
		
		JButton btnQGManager = new JButton(new ImageIcon(((new ImageIcon("resources/settings.jpg")).getImage()).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));  
		btnQGManager.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//JQGManager jm = new JQGManager();
				self.setVisible(false);
				//jm.setVisible(true);
			}
		});
		contentPane.add(btnQGManager);
		
		JButton btnJouer = new JButton(new ImageIcon(((new ImageIcon("resources/play-icon.png")).getImage()).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));  
		btnJouer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Patient p = (Patient)cbPatient.getSelectedItem();
				//JJouer jj = new JJouer();
				self.setVisible(false);
				//jj.setPatient(p);
				//jj.setVisible(true);
				//jj.jouer();
			}
		});
		contentPane.add(btnJouer);
		
		cbPatient = new JComboBox();
		//List<Patient> ps = PatientControl.findAll();
		//for (Patient p: ps){
		//	cbPatient.addItem(p);
		//}
		contentPane.add(cbPatient);
	}

}
