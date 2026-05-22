package com.project.erqueue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.erqueue.model.PatientLog;
import com.project.erqueue.repository.PatientLogRepository;

@RestController
public class LogController {

    @Autowired
    private PatientLogRepository logRepository;

    @GetMapping("/logs/recent")
    public List<PatientLog> getRecentLogs() {

        return logRepository
            .findTop10ByOrderByTimestampDesc();
    }
}