package model;

import java.time.LocalDate;

public class MedicalRecords {
    private int id;
    private int patientId;
    private String diagnosis;
    private String prescription;
    private LocalDate recordDate;

    public MedicalRecords(int patientId, String diagnosis, String prescription, LocalDate recordDate) {
        this.patientId = patientId;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.recordDate = recordDate;
    }

    public MedicalRecords(int id, int patientId, String diagnosis, String prescription, LocalDate recordDate) {
        this.id = id;
        this.patientId = patientId;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.recordDate = recordDate;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }
}