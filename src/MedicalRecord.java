public class MedicalRecord {
    private int recordId;
    private String diagnosis;
    private String prescription;
    private int patientId;

    public MedicalRecord(int recordId, String diagnosis, String prescription, int patientId) {
        this.recordId = recordId;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.patientId = patientId;
        
    }

    public int getRecordId() {
        return recordId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    @Override
    public String toString() {
        String diagDisplay;
        if (diagnosis == null || diagnosis.isEmpty()) {
            diagDisplay = "N/A";
        } else {
            diagDisplay = diagnosis;
        }

        String presDisplay;
        if (prescription == null || prescription.isEmpty()) {
            presDisplay = "N/A";
        } else {
            presDisplay = prescription;
        }
        
        return "  Record ID: " + recordId + "\n" +
               "  Diagnosis: " + diagDisplay + "\n" +
               "  Prescription: " + presDisplay;
    }
}
