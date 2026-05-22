package com.project.erqueue.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.project.erqueue.model.Patient;

@Service
public class AlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertService.class);

    public void checkAndAlert(Patient p) {

        // 🚨 Critical patient alert
        if (p.getSeverity() >= 9) {
            log.warn("🚨 CRITICAL ALERT: Patient ID={}, Severity={}", 
                     p.getId(), p.getSeverity());
        }

        // You can extend later:
        // - long waiting alerts
        // - department overload alerts
    }
}