public class Patient extends Person implements Billable {
    private int patientId;
    private MedicalRecord medicalRecord;

    public Patient(String name, int age, int patientId, MedicalRecord medicalRecord) {
        super(name, age);
        this.patientId = patientId;
        this.medicalRecord = medicalRecord;
    }


    public String getRole() {
        return "Patient";
    }

    public int getPatientId() {
        return patientId;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    @Override
    public double calculateBill() {
        return 150.0;
    }

    @Override
    public String toString() {
        String output = "Patient Details:\n";
        output += " Name: " + getName() + "\n";
        output += " Age: " + getAge() + "\n";
        output += " Patient ID: " + patientId + "\n";
        output += "Medical Record Details:\n";
        if (medicalRecord != null) {
            output += medicalRecord.toString();
        } else {
            output += "  N/A";
        }
        return output;
    }
}
