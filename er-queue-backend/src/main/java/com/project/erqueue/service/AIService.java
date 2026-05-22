package com.project.erqueue.service;

import org.springframework.stereotype.Service;

@Service
public class AIService {

    public int predictSeverity(String symptoms) {

        if (symptoms == null) return 3;

        String s = symptoms.toLowerCase();
        int severity = 3;

        if (s.contains("chest pain")) severity += 4;
        if (s.contains("breathing")) severity += 3;
        if (s.contains("bleeding")) severity += 3;

        return Math.min(severity, 10);
    }
}