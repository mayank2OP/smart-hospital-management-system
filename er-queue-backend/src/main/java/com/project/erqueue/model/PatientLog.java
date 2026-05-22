package com.project.erqueue.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class PatientLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;

    private String action;

    private LocalDateTime timestamp;

    public PatientLog() {
    }

    public PatientLog(Long patientId, String action, LocalDateTime timestamp) {
        this.patientId = patientId;
        this.action = action;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}