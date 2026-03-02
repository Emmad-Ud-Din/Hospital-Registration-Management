import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class HospitalFrame extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	private JLabel patientCountLabel;
	private JLabel staffCountLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HospitalFrame frame = new HospitalFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HospitalFrame() {
		setTitle("Hospital Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 671, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton displayPatientsBtn = new JButton("Display Patients");
		displayPatientsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String patientData = HospitalSystem.displayPatients();
				textArea.setText(patientData);
			}
		});
		displayPatientsBtn.setBounds(10, 21, 180, 23);
		contentPane.add(displayPatientsBtn);
		
		JButton displayStaffBtn = new JButton("Display Staff");
		displayStaffBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String staffData = HospitalSystem.displayStaff();
				textArea.setText(staffData);
			}
		});
		displayStaffBtn.setBounds(469, 21, 180, 23);
		contentPane.add(displayStaffBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 55, 629, 300);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		JButton openAddFrameBtn = new JButton("Add New Staff Or Patient");
		openAddFrameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddFrame addFrame = new AddFrame(HospitalFrame.this); 
				addFrame.setVisible(true);
				HospitalFrame.this.setVisible(false); // Hide HospitalFrame
			}
		});
		openAddFrameBtn.setBounds(10, 382, 180, 23);
		contentPane.add(openAddFrameBtn);
		
		JButton openSearchFramebtn = new JButton("Search");
		openSearchFramebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchFrame searchFrame = new SearchFrame(HospitalFrame.this);
				searchFrame.setVisible(true);
				HospitalFrame.this.setVisible(false); // Hide HospitalFrame
			}
		});
		openSearchFramebtn.setBounds(500, 382, 149, 23);
		contentPane.add(openSearchFramebtn);
		
		patientCountLabel = new JLabel("Total Patients: 0");
		patientCountLabel.setBounds(20, 420, 200, 14); 
		contentPane.add(patientCountLabel);
		
		staffCountLabel = new JLabel("Total Staff: 0");
		staffCountLabel.setBounds(230, 420, 200, 14); 
		contentPane.add(staffCountLabel);
		
		updateCounts();
	}
	
	private void updateCounts() {
		patientCountLabel.setText("Total Patients: " + HospitalSystem.getTotalPatientCount());
		staffCountLabel.setText("Total Staff: " + HospitalSystem.getTotalStaffCount());
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) { // If frame is being made visible
			updateCounts();
		}
	}

}
