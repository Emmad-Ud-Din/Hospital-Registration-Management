import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class AddMedicalRecord extends JFrame {

	private JPanel contentPane;
	private JTextField recordIdField;
	private JTextField diagnosisField;
	private JTextField prescriptionField;
	private JLabel recordIdLabel;
	private JLabel diagnosisLabel;
	private JLabel prescriptionLabel;
	private JButton saveRecordButton;
	private Patient currentPatient;
	private JLabel statusLabel;
	private JFrame parentFrame;
	private JButton backButton;




	/**
	 * Create the frame.
	 */
	public AddMedicalRecord(Patient patient, JFrame parent) {
		this.currentPatient = patient;
		this.parentFrame = parent;

		setTitle("Medical Record for Patient ID: " + patient.getPatientId());

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(150, 150, 450, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		recordIdLabel = new JLabel("Record ID:");
		recordIdLabel.setBounds(10, 27, 111, 14);
		contentPane.add(recordIdLabel);

		recordIdField = new JTextField();
		recordIdField.setColumns(10);
		recordIdField.setBounds(120, 24, 96, 20);
		recordIdField.setEditable(false);
		contentPane.add(recordIdField);
		
		diagnosisLabel = new JLabel("Diagnosis:");
		diagnosisLabel.setBounds(10, 58, 111, 14);
		contentPane.add(diagnosisLabel);

		diagnosisField = new JTextField();
		diagnosisField.setColumns(10);
		diagnosisField.setBounds(120, 55, 250, 20);
		contentPane.add(diagnosisField);
		
		prescriptionLabel = new JLabel("Prescription:");
		prescriptionLabel.setBounds(10, 89, 111, 14);
		contentPane.add(prescriptionLabel);

		prescriptionField = new JTextField();
		prescriptionField.setColumns(10);
		prescriptionField.setBounds(120, 86, 250, 20);
		contentPane.add(prescriptionField);
		
		saveRecordButton = new JButton("Save Record");
		saveRecordButton.setBounds(150, 130, 139, 23);
		contentPane.add(saveRecordButton);

		statusLabel = new JLabel(" ");
		statusLabel.setBounds(10, 200, 400, 14);
		contentPane.add(statusLabel);

		backButton = new JButton("Back");
		backButton.setBounds(150, 164, 139, 23);
		contentPane.add(backButton);

		if (currentPatient != null && currentPatient.getMedicalRecord() != null) {
			recordIdField.setText(String.valueOf(currentPatient.getMedicalRecord().getRecordId()));
			diagnosisField.setText(currentPatient.getMedicalRecord().getDiagnosis());
			prescriptionField.setText(currentPatient.getMedicalRecord().getPrescription());
		} else if (currentPatient != null) {
			recordIdField.setText(String.valueOf(currentPatient.getPatientId()));
		}

		saveRecordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statusLabel.setText(" ");
				if (currentPatient == null) {
					statusLabel.setText("No patient context! Error.");
					return;
				}
				String diagnosis = diagnosisField.getText();
				String prescription = prescriptionField.getText();

				if (diagnosis.isEmpty() || prescription.isEmpty()) {
					statusLabel.setText("Diagnosis and Prescription cannot be empty.");
					return;
				}

				MedicalRecord mr = currentPatient.getMedicalRecord();
				if (mr != null) {
					mr.setDiagnosis(diagnosis);
					mr.setPrescription(prescription);
					statusLabel.setText("Medical Record updated successfully!");
				} else {
					statusLabel.setText("Error: Patient has no medical record to update.");
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
}
