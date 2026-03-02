import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class HospitalSystem {
    private static HashMap<Integer, Patient> patients = new HashMap<>();
    private static HashMap<Integer, Staff> staffList = new HashMap<>();

    public static Set<Integer> getPatientIds() {
        return patients.keySet();
    }

    public static Set<Integer> getStaffIds() {
        return staffList.keySet();
    }

    public static void addPatient(Patient p) {
        patients.put(p.getPatientId(), p);
    }

    public static String displayPatients() {
        String output = "Patients:\n";
        if (patients.isEmpty()) {
            return "No patients in the system.\n";
        }
        Iterator<Map.Entry<Integer, Patient>> iterator = patients.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Patient> entry = iterator.next();
            output += entry.getValue().toString();
            output += "\n------------\n";
        }
        return output;
    }

    public static Patient searchPatient(int id) {
        return patients.get(id);
    }

    public static boolean deletePatient(int id) {
        if (patients.containsKey(id)) {
            patients.remove(id);
            return true;
        }
        return false;
    }

    public static void addStaff(Staff s) {
        staffList.put(s.getStaffId(), s);
    }

    public static String displayStaff() {
        String output = "Staff:\n";
        if (staffList.isEmpty()) {
            return "No staff in the system.\n";
        }
        Iterator<Map.Entry<Integer, Staff>> iterator = staffList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Staff> entry = iterator.next();
            output += entry.getValue().toString();
            output += "\n------------\n";
        }
        return output;
    }

    public static Staff searchStaff(int id) {
        return staffList.get(id);
    }

    public static boolean deleteStaff(int id) {
        return staffList.remove(id) != null;
    }

    public static double calculateAllBills() {
        double total = 0;
        for (Patient p : patients.values()) {
            total += p.calculateBill();
        }
        return total;
    }

    public static int getTotalPatientCount() {
        return patients.size();
    }

    public static int getTotalStaffCount() {
        return staffList.size();
    }
}
