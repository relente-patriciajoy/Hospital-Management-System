package app;

public class Main {
    public static void main(String[] args) {
        Patient newPatient = new Patient("Alice", 30, "Female", "09123456789");
        boolean success = PatientDAO.addPatient(newPatient);

        if (success) {
            System.out.println("Patient inserted successfully.");
        } else {
            System.out.println("Failed to insert patient.");
        }
    }
}