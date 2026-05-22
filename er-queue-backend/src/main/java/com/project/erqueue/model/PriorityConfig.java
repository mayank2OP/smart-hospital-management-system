package com.project.erqueue.model;

import jakarta.persistence.*;

@Entity
public class PriorityConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double severityWeight;
    private double waitingWeight;
    private double ageWeight;

    private int timeMultiplier; // optional but useful

    // 🔹 Getters & Setters
    public Long getId() {
        return id;
    }

    public double getSeverityWeight() {
        return severityWeight;
    }

    public void setSeverityWeight(double severityWeight) {
        this.severityWeight = severityWeight;
    }

    public double getWaitingWeight() {
        return waitingWeight;
    }

    public void setWaitingWeight(double waitingWeight) {
        this.waitingWeight = waitingWeight;
    }

    public double getAgeWeight() {
        return ageWeight;
    }

    public void setAgeWeight(double ageWeight) {
        this.ageWeight = ageWeight;
    }

    public int getTimeMultiplier() {
        return timeMultiplier;
    }

    public void setTimeMultiplier(int timeMultiplier) {
        this.timeMultiplier = timeMultiplier;
    }
}