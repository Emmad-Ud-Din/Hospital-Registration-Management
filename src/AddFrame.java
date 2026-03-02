import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

public class AddFrame extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField ageField;
	private JTextField idField;
	private JTextField departmentField;
	private JLabel nameLabel;
	private JLabel ageLabel;
	private JLabel idLabel;
	private JLabel departmentLabel;
	private JRadioButton patientRadioButton;
	private JRadioButton staffRadioButton;
	private final ButtonGroup personTypeGroup = new ButtonGroup();
	private JButton addButton;
	private JButton addMedicalRecordButton;
	private JLabel statusLabel;
	private Patient currentPatientForMedicalRecord = null;
	private JFrame parentFrame;
	private JButton backButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame dummyParent = new JFrame();
					dummyParent.setVisible(false);
					AddFrame frame = new AddFrame(dummyParent);
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
	public AddFrame(JFrame parent) {
		this.parentFrame = parent;

		setTitle("Add New Person");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		nameLabel = new JLabel("Name:");
		nameLabel.setBounds(20, 14, 111, 14);
		contentPane.add(nameLabel);

		nameField = new JTextField();
		nameField.setBounds(168, 11, 150, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		ageLabel = new JLabel("Age:");
		ageLabel.setBounds(20, 45, 111, 14);
		contentPane.add(ageLabel);

		ageField = new JTextField();
		ageField.setColumns(10);
		ageField.setBounds(168, 42, 80, 20);
		contentPane.add(ageField);
		
		idLabel = new JLabel("ID:");
		idLabel.setBounds(20, 76, 111, 14);
		contentPane.add(idLabel);

		idField = new JTextField();
		idField.setColumns(10);
		idField.setBounds(168, 73, 80, 20);
		contentPane.add(idField);
		
		departmentLabel = new JLabel("Department:");
		departmentLabel.setBounds(20, 107, 111, 14);
		contentPane.add(departmentLabel);

		departmentField = new JTextField();
		departmentField.setColumns(10);
		departmentField.setBounds(168, 104, 150, 20);
		contentPane.add(departmentField);
		
		patientRadioButton = new JRadioButton("Patient");
		personTypeGroup.add(patientRadioButton);
		patientRadioButton.setBounds(330, 10, 109, 23);
		contentPane.add(patientRadioButton);
		
		staffRadioButton = new JRadioButton("Staff");
		personTypeGroup.add(staffRadioButton);
		staffRadioButton.setBounds(330, 41, 109, 23);
		contentPane.add(staffRadioButton);
		
		addButton = new JButton("Add");
		addButton.setBounds(168, 150, 150, 23);
		contentPane.add(addButton);
		
		addMedicalRecordButton = new JButton("Add Medical Record");
		addMedicalRecordButton.setBounds(168, 184, 150, 23);
		contentPane.add(addMedicalRecordButton);
		addMedicalRecordButton.setEnabled(false);

		statusLabel = new JLabel(" ");
		statusLabel.setBounds(20, 250, 450, 14);
		contentPane.add(statusLabel);

		backButton = new JButton("Back");
		backButton.setBounds(168, 218, 150, 23);
		contentPane.add(backButton);

		ItemListener radioListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				statusLabel.setText(" ");
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (patientRadioButton.isSelected()) {
						departmentLabel.setVisible(false);
						departmentField.setVisible(false);
						addMedicalRecordButton.setEnabled(true);
						idLabel.setText("Patient ID:");
					} else if (staffRadioButton.isSelected()) {
						departmentLabel.setVisible(true);
						departmentField.setVisible(true);
						addMedicalRecordButton.setEnabled(false);
						idLabel.setText("Staff ID:");
					}
				}
			}
		};
		patientRadioButton.addItemListener(radioListener);
		staffRadioButton.addItemListener(radioListener);
		
		patientRadioButton.setSelected(true);
		departmentLabel.setVisible(false);
		departmentField.setVisible(false);
		idLabel.setText("Patient ID:");

		// ActionListener for Enter key in text fields
		ActionListener enterKeyListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addButton.doClick(); // Programmatically click the Add button
			}
		};

		nameField.addActionListener(enterKeyListener);
		ageField.addActionListener(enterKeyListener);
		idField.addActionListener(enterKeyListener);
		departmentField.addActionListener(enterKeyListener);

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statusLabel.setText(" ");
				String name = nameField.getText();
				String ageStr = ageField.getText();
				String idStr = idField.getText();

				if (name.isEmpty() || ageStr.isEmpty() || idStr.isEmpty()) {
					statusLabel.setText("Name, Age, and ID are required.");
					return;
				}

				try {
					int age = Integer.parseInt(ageStr);
					int idVal = Integer.parseInt(idStr);

					if (age <= 0 || idVal <= 0) {
						statusLabel.setText("Age and ID must be positive numbers.");
						return;
					}

					if (patientRadioButton.isSelected()) {
						MedicalRecord placeholderRecord = new MedicalRecord(idVal, "", "", idVal);
						Patient patient = new Patient(name, age, idVal, placeholderRecord);
						HospitalSystem.addPatient(patient);
						currentPatientForMedicalRecord = patient;
						statusLabel.setText("Patient core details added. Click 'Add Medical Record' for more.");
					} else if (staffRadioButton.isSelected()) {
						String department = departmentField.getText();
						if (department.isEmpty()) {
							statusLabel.setText("Department is required for Staff.");
							return;
						}
						Staff staff = new Staff(name, age, idVal, department);
						HospitalSystem.addStaff(staff);
						statusLabel.setText("Staff added successfully!");
						clearFields();
						currentPatientForMedicalRecord = null;
					} else {
						statusLabel.setText("Please select Patient or Staff.");
					}

				} catch (NumberFormatException ex) {
					statusLabel.setText("Age and ID must be valid numbers.");
				}
			}
		});

		addMedicalRecordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statusLabel.setText(" ");
				if (patientRadioButton.isSelected() && !idField.getText().isEmpty()) {
					try {
						int patientId = Integer.parseInt(idField.getText());
						Patient targetPatient = HospitalSystem.searchPatient(patientId);

						if (targetPatient == null && currentPatientForMedicalRecord != null && currentPatientForMedicalRecord.getPatientId() == patientId) {
							targetPatient = currentPatientForMedicalRecord;
						}
						
						if (targetPatient != null) {
							AddMedicalRecord amrFrame = new AddMedicalRecord(targetPatient, AddFrame.this);
							amrFrame.setVisible(true);
							AddFrame.this.setVisible(false);
						} else {
							statusLabel.setText("Patient with ID " + patientId + " not found or not yet added.");
						}
					} catch (NumberFormatException ex) {
						statusLabel.setText("Patient ID must be a valid number for medical record.");
					}
				} else {
					statusLabel.setText("Select 'Patient' and ensure Patient ID is entered.");
				}
			}
		});

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (parentFrame != null) {
					parentFrame.setVisible(true);
				}
				dispose();
			}
		});
	}

	private void clearFields() {
		nameField.setText("");
		ageField.setText("");
		idField.setText("");
		departmentField.setText("");
		patientRadioButton.setSelected(true);
		statusLabel.setText(" ");
		currentPatientForMedicalRecord = null;
	}
}
