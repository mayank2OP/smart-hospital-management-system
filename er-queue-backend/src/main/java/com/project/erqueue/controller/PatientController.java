package com.project.erqueue.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.erqueue.dto.PatientRequestDTO;
import com.project.erqueue.dto.PatientResponseDTO;
import com.project.erqueue.response.ApiResponse;
import com.project.erqueue.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService service;

    // =====================================
    // ADD PATIENT
    // =====================================

    @PostMapping
    public ApiResponse<PatientResponseDTO>
    addPatient(

            @Valid
            @RequestBody
            PatientRequestDTO req

    ) {

        return new ApiResponse<>(

                "Patient added successfully",

                service.addPatient(req)
        );
    }

    // =====================================
    // QUEUE BY DEPARTMENT
    // =====================================

    @GetMapping("/queue/{deptId}")
    public ApiResponse<List<PatientResponseDTO>>
    getQueue(

            @PathVariable Long deptId

    ) {

        return new ApiResponse<>(

                "Active queue fetched successfully",

                service.getSortedQueue(deptId)
        );
    }

    // =====================================
    // FULL ACTIVE QUEUE
    // =====================================

    @GetMapping("/queue")
    public ApiResponse<List<PatientResponseDTO>>
    fullQueue() {

        return new ApiResponse<>(

                "Active queue fetched successfully",

                service.getSortedQueue()
        );
    }

    // =====================================
    // NEXT PATIENT
    // =====================================

    @GetMapping("/next/{deptId}")
    public ApiResponse<PatientResponseDTO>
    getNext(

            @PathVariable Long deptId

    ) {

        return new ApiResponse<>(

                "Next patient fetched successfully",

                service.getNextPatient(deptId)
        );
    }

    // =====================================
    // MARK PATIENT AS TREATED
    // =====================================

    @PutMapping("/{id}/treat")
    public ApiResponse<PatientResponseDTO>
    treat(

            @PathVariable Long id

    ) {

        return new ApiResponse<>(

                "Patient treated successfully",

                service.treatPatient(id)
        );
    }

    // =====================================
    // OVERRIDE PRIORITY
    // =====================================

    @PutMapping("/{id}/override")
    public ApiResponse<PatientResponseDTO>
    override(

            @PathVariable Long id,

            @RequestParam int severity

    ) {

        return new ApiResponse<>(

                "Priority updated successfully",

                service.overridePriority(
                        id,
                        severity
                )
        );
    }

    // =====================================
    // CRITICAL PATIENTS
    // =====================================

    @GetMapping("/critical")
    public ApiResponse<List<PatientResponseDTO>>
    criticalPatients() {

        return new ApiResponse<>(

                "Critical patients fetched successfully",

                service.getCriticalPatients()
        );
    }

    // =====================================
    // LONG WAITING PATIENTS
    // =====================================

    @GetMapping("/long-waiting")
    public ApiResponse<List<PatientResponseDTO>>
    longWaitingPatients() {

        return new ApiResponse<>(

                "Long waiting patients fetched successfully",

                service.getLongWaitingPatients()
        );
    }

    // =====================================
    // WAIT TIME PREDICTION
    // =====================================

    @GetMapping("/{id}/wait-time")
    public ApiResponse<Map<String, Object>>
    predictWaitTime(

            @PathVariable Long id

    ) {

        return new ApiResponse<>(

                "Wait time predicted successfully",

                service.predictWaitTime(id)
        );
    }

    // =====================================
    // DEPARTMENT LOAD
    // =====================================

    @GetMapping("/department-load")
    public ApiResponse<Map<String, Long>>
    departmentLoad() {

        return new ApiResponse<>(

                "Department load fetched successfully",

                service.getDepartmentLoad()
        );
    }

    // =====================================
    // ANALYTICS
    // =====================================

    @GetMapping("/analytics")
    public ApiResponse<Map<String, Object>>
    analytics() {

        return new ApiResponse<>(

                "Analytics fetched successfully",

                service.getAnalytics()
        );
    }

    // =====================================
    // QUEUE INSIGHTS
    // =====================================

    @GetMapping("/queue-insights")
    public ApiResponse<Map<String, Object>>
    queueInsights() {

        return new ApiResponse<>(

                "Queue insights fetched successfully",

                service.getQueueInsights()
        );
    }
}