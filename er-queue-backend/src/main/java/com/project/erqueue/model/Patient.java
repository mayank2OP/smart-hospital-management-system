package com.project.erqueue.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String gender;

    private String complaint;

    private Integer painLevel;

    private String breathing;

    private String consciousness;

    private int severity;

    private int finalSeverity;

    // UTC SAFE
    private Instant arrivalTime;

    // =========================
    // PRODUCTION FIELDS
    // =========================

    private String assignedDoctor;

    private String medicalCondition;

    private boolean critical;

    // UTC SAFE
    private Instant createdAt;

    private Instant updatedAt;

    @ManyToOne
    private Department department;

    @Enumerated(EnumType.STRING)
    private Status status;

    // =========================
    // AUTO TIMESTAMPS
    // =========================

    @PrePersist
    public void onCreate() {

        Instant now = Instant.now();

        createdAt = now;

        updatedAt = now;

        if (arrivalTime == null) {
            arrivalTime = now;
        }

        // DEFAULT SAFE STATUS
        if (status == null) {
            status = Status.WAITING;
        }
    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = Instant.now();
    }

    // =====================
    // GETTERS & SETTERS
    // =====================

    public Long getId() {
        return id;
    }

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

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(
        int severity
    ) {
        this.severity = severity;
    }

    public int getFinalSeverity() {
        return finalSeverity;
    }

    public void setFinalSeverity(
        int finalSeverity
    ) {
        this.finalSeverity =
            finalSeverity;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(
        Instant arrivalTime
    ) {
        this.arrivalTime =
            arrivalTime;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(
        Department department
    ) {
        this.department =
            department;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(
        Status status
    ) {
        this.status = status;
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

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(
        boolean critical
    ) {
        this.critical = critical;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
        Instant createdAt
    ) {
        this.createdAt =
            createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
        Instant updatedAt
    ) {
        this.updatedAt =
            updatedAt;
    }
}