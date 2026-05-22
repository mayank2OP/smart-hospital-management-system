package com.project.erqueue.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PatientRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Min(
        value = 1,
        message = "Age must be greater than 0"
    )
    private int age;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Complaint is required")
    private String complaint;

    @NotNull(message = "Pain level is required")
    private Integer painLevel;

    @NotBlank(message = "Breathing status is required")
    private String breathing;

    @NotBlank(message = "Consciousness status is required")
    private String consciousness;

    @NotNull(message = "Department is required")
    private Long departmentId;

    // =========================
    // NEW FIELDS
    // =========================

    private String assignedDoctor;

    private String medicalCondition;

    private Boolean critical = false;

    private Boolean emergencyOverride =
        false;

    // =========================
    // GETTERS + SETTERS
    // =========================

    public String getName() {
        return name;
    }

    public void setName(
        String name
    ) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(
        int age
    ) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(
        String gender
    ) {
        this.gender = gender;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(
        String complaint
    ) {
        this.complaint = complaint;
    }

    public Integer getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(
        Integer painLevel
    ) {
        this.painLevel = painLevel;
    }

    public String getBreathing() {
        return breathing;
    }

    public void setBreathing(
        String breathing
    ) {
        this.breathing = breathing;
    }

    public String getConsciousness() {
        return consciousness;
    }

    public void setConsciousness(
        String consciousness
    ) {
        this.consciousness =
            consciousness;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(
        Long departmentId
    ) {
        this.departmentId =
            departmentId;
    }

    public String getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(
        String assignedDoctor
    ) {
        this.assignedDoctor =
            assignedDoctor;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(
        String medicalCondition
    ) {
        this.medicalCondition =
            medicalCondition;
    }
    public Boolean getCritical() {
        return critical;
    }

    public void setCritical(
        Boolean critical
    ) {
        this.critical = critical;
    }

    public Boolean getEmergencyOverride() {
        return emergencyOverride;
    }

    public void setEmergencyOverride(
        Boolean emergencyOverride
    ) {
        this.emergencyOverride =
            emergencyOverride;
    }
}