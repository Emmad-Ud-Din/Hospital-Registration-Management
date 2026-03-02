import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

public class SearchFrame extends JFrame {

	private JPanel contentPane;
	private JComboBox<Integer> idComboBox;
	private JRadioButton patientSearchRadioButton;
	private JRadioButton staffSearchRadioButton;
	private ButtonGroup searchTypeGroup = new ButtonGroup();
	private JTextArea resultsDisplayArea;
	private JButton backButton;
	private JButton deleteButton;
	private JLabel statusLabel;
	private JFrame parentFrame;



	/**
	 * Create the frame.
	 */
	public SearchFrame(JFrame parent) {
		this.parentFrame = parent;

		setTitle("View Details / Delete");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel selectTypeLabel = new JLabel("Select Type:");
		selectTypeLabel.setBounds(10, 11, 80, 14);
		contentPane.add(selectTypeLabel);

		patientSearchRadioButton = new JRadioButton("Patient");
		searchTypeGroup.add(patientSearchRadioButton);
		patientSearchRadioButton.setBounds(90, 7, 100, 23);
		contentPane.add(patientSearchRadioButton);
		
		staffSearchRadioButton = new JRadioButton("Staff");
		searchTypeGroup.add(staffSearchRadioButton);
		staffSearchRadioButton.setBounds(200, 7, 100, 23);
		contentPane.add(staffSearchRadioButton);

		JLabel selectIdLabel = new JLabel("Select ID:");
		selectIdLabel.setBounds(10, 41, 80, 14);
		contentPane.add(selectIdLabel);

		idComboBox = new JComboBox<>();
		idComboBox.setBounds(90, 37, 120, 22);
		contentPane.add(idComboBox);

		deleteButton = new JButton("Delete Selected");
		deleteButton.setBounds(230, 37, 150, 23);
		deleteButton.setEnabled(false);
		contentPane.add(deleteButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 460, 250);
		contentPane.add(scrollPane);

		resultsDisplayArea = new JTextArea();
		resultsDisplayArea.setEditable(false);
		scrollPane.setViewportView(resultsDisplayArea);
		
		statusLabel = new JLabel(" ");
		statusLabel.setBounds(10, 330, 460, 14);
		contentPane.add(statusLabel);
		
		backButton = new JButton("Back");
		backButton.setBounds(380, 360, 89, 23);
		contentPane.add(backButton);

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (parentFrame != null) {
					parentFrame.setVisible(true);
				}
				dispose();
			}
		});

		ItemListener radioListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					populateIdComboBox();
					resultsDisplayArea.setText("");
					deleteButton.setEnabled(false);
					statusLabel.setText(" ");
				}
			}
		};
		patientSearchRadioButton.addItemListener(radioListener);
		staffSearchRadioButton.addItemListener(radioListener);

		idComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displaySelectedIdDetails();
				statusLabel.setText(" ");
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statusLabel.setText(" ");
				performDelete();
			}
		});
		
		patientSearchRadioButton.setSelected(true);
	}

	private void populateIdComboBox() {
		idComboBox.removeAllItems();
		resultsDisplayArea.setText("");
		deleteButton.setEnabled(false);
		statusLabel.setText(" ");

		if (patientSearchRadioButton.isSelected()) {
			Set<Integer> patientIds = HospitalSystem.getPatientIds();
			if (!patientIds.isEmpty()) {
				for (Integer id : patientIds) {
					idComboBox.addItem(id);
				}
			} else {
				statusLabel.setText("No patients in the system to display.");
			}
		} else if (staffSearchRadioButton.isSelected()) {
			Set<Integer> staffIds = HospitalSystem.getStaffIds();
			if (!staffIds.isEmpty()) {
				for (Integer id : staffIds) {
					idComboBox.addItem(id);
				}
			} else {
				statusLabel.setText("No staff in the system to display.");
			}
		}
		
		if (idComboBox.getItemCount() > 0) {
			idComboBox.setSelectedIndex(0);
		} else {
			resultsDisplayArea.setText("");
			statusLabel.setText("No IDs to display for selected type.");
			deleteButton.setEnabled(false);
		}
	}

	private void displaySelectedIdDetails() {
		Integer selectedId = (Integer) idComboBox.getSelectedItem();
		resultsDisplayArea.setText("");
		deleteButton.setEnabled(false);
		statusLabel.setText(" ");

		if (selectedId == null) {
			statusLabel.setText("Please select an ID from the list.");
			return;
		}

		if (patientSearchRadioButton.isSelected()) {
			Patient patient = HospitalSystem.searchPatient(selectedId);
			if (patient != null) {
				resultsDisplayArea.setText(patient.toString());
				deleteButton.setEnabled(true);
			} else {
				statusLabel.setText("Patient with ID " + selectedId + " not found.");
			}
		} else if (staffSearchRadioButton.isSelected()) {
			Staff staff = HospitalSystem.searchStaff(selectedId);
			if (staff != null) {
				resultsDisplayArea.setText(staff.toString());
				deleteButton.setEnabled(true);
			} else {
				statusLabel.setText("Staff with ID " + selectedId + " not found.");
			}
		}
	}

	private void performDelete() {
		Integer selectedId = (Integer) idComboBox.getSelectedItem();
		if (selectedId == null) {
			statusLabel.setText("No ID selected for deletion.");
			return;
		}

		boolean deleted = false;
		String itemType = "";

		if (patientSearchRadioButton.isSelected()) {
			itemType = "Patient";
			deleted = HospitalSystem.deletePatient(selectedId);
		} else if (staffSearchRadioButton.isSelected()) {
			itemType = "Staff";
			deleted = HospitalSystem.deleteStaff(selectedId);
		} else {
			statusLabel.setText("Error: No type selected for deletion.");
			return;
		}

		if (deleted) {
			statusLabel.setText(itemType + " with ID " + selectedId + " deleted successfully.");
			populateIdComboBox();
		} else {
			statusLabel.setText("Could not delete " + itemType + " with ID " + selectedId + ". (May already be deleted)");
			populateIdComboBox();
		}
	}
}
