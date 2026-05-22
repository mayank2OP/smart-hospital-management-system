package com.project.erqueue.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.erqueue.service.PatientService;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private PatientService service;

    @GetMapping("/analytics")
    public Map<String, Object> analytics() {
        return service.getAnalytics();
    }
}