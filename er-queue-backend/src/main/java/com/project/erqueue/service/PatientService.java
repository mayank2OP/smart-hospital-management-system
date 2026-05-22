package com.project.erqueue.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.erqueue.dto.PatientRequestDTO;
import com.project.erqueue.dto.PatientResponseDTO;
import com.project.erqueue.model.Department;
import com.project.erqueue.model.Patient;
import com.project.erqueue.model.PatientLog;
import com.project.erqueue.model.Status;
import com.project.erqueue.repository.DepartmentRepository;
import com.project.erqueue.repository.PatientLogRepository;
import com.project.erqueue.repository.PatientRepository;

@Service
public class PatientService {

    private static final Logger log =
            LoggerFactory.getLogger(
                    PatientService.class
            );

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientLogRepository logRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // =====================================
    // DTO MAPPER
    // =====================================

    public PatientResponseDTO mapToDTO(
            Patient p
    ) {

        long waitingTime = Duration
                .between(
                        p.getArrivalTime(),
                        Instant.now()
                )
                .toMinutes();

        PatientResponseDTO dto =
                new PatientResponseDTO();

        dto.setId(p.getId());

        dto.setName(p.getName());

        dto.setAge(p.getAge());

        dto.setSeverity(
                p.getFinalSeverity()
        );

        dto.setStatus(
                p.getStatus().name()
        );

        if (p.getDepartment() != null) {

            dto.setDepartmentId(
                    p.getDepartment().getId()
            );
        }

        dto.setWaitingTime(waitingTime);

        return dto;
    }

    // =====================================
    // TRIAGE ENGINE
    // =====================================

    private int calculateSeverity(
            PatientRequestDTO req
    ) {

        double severity = 0;

        // =========================
        // COMPLAINT WEIGHTS
        // =========================

        if ("Fever".equals(
                req.getComplaint()
        )) {

            severity += 1;

        } else if ("Head Injury".equals(
                req.getComplaint()
        )) {

            severity += 3;

        } else if ("Chest Pain".equals(
                req.getComplaint()
        )) {

            severity += 2;

        } else if ("Breathing Difficulty".equals(
                req.getComplaint()
        )) {

            severity += 3;

        } else if ("Accident / Trauma".equals(
                req.getComplaint()
        )) {

            severity += 4;

        } else if ("Abdominal Pain".equals(
                req.getComplaint()
        )) {

            severity += 2;
        }

        // =========================
        // BREATHING
        // =========================

        if ("Severe Distress".equals(
                req.getBreathing()
        )) {

            severity += 4;

        } else if ("Difficulty".equals(
                req.getBreathing()
        )) {

            severity += 2;
        }

        // =========================
        // CONSCIOUSNESS
        // =========================

        if ("Unconscious".equals(
                req.getConsciousness()
        )) {

            severity += 5;

        } else if ("Drowsy".equals(
                req.getConsciousness()
        )) {

            severity += 2;
        }

        // =========================
        // PAIN CONTRIBUTION
        // =========================

        severity += Math.min(
                req.getPainLevel() / 2.0,
                2
        );

        // =========================
        // EMERGENCY OVERRIDE
        // =========================

        if (Boolean.TRUE.equals(
                req.getEmergencyOverride()
        )) {

            severity = 10;
        }

        // =========================
        // CRITICAL PATIENT
        // =========================

        if (Boolean.TRUE.equals(
                req.getCritical()
        )) {

            severity = 10;
        }

        // =========================
        // FINAL NORMALIZATION
        // =========================

        severity = Math.min(
                severity,
                10
        );

        return (int) Math.round(severity);
    }

    // =====================================
    // ADD PATIENT
    // =====================================

    public PatientResponseDTO addPatient(
            PatientRequestDTO req
    ) {

        log.info(
                "Adding patient: {}",
                req.getName()
        );

        if (req.getComplaint() == null) {

            throw new RuntimeException(
                    "Complaint required"
            );
        }

        if (req.getPainLevel() == null) {

            throw new RuntimeException(
                    "Pain level required"
            );
        }

        Department dept =
                departmentRepository.findById(
                        req.getDepartmentId()
                ).orElseThrow(() ->

                        new RuntimeException(
                                "Department not found"
                        )
                );

        Patient patient = new Patient();

        // BASIC INFO

        patient.setName(req.getName());

        patient.setAge(req.getAge());

        patient.setGender(
                req.getGender()
        );

        // TRIAGE

        patient.setComplaint(
                req.getComplaint()
        );

        patient.setPainLevel(
                req.getPainLevel()
        );

        patient.setBreathing(
                req.getBreathing()
        );

        patient.setConsciousness(
                req.getConsciousness()
        );

        patient.setDepartment(dept);

        // =========================
        // EXTRA FIELDS
        // =========================

        patient.setAssignedDoctor(
                req.getAssignedDoctor()
        );

        patient.setMedicalCondition(
                req.getMedicalCondition()
        );

        patient.setCritical(
                Boolean.TRUE.equals(
                        req.getCritical()
                )
        );

        int severity =
                calculateSeverity(req);

        patient.setSeverity(severity);

        patient.setFinalSeverity(
                severity
        );

        // UTC SAFE

        patient.setArrivalTime(
                Instant.now()
        );

        // IMPORTANT

        patient.setStatus(
                Status.WAITING
        );

        Patient saved;

        try {

            saved =
                    patientRepository.saveAndFlush(
                            patient
                    );

        } catch (Exception e) {

            e.printStackTrace();

            throw e;
        }

        log("ADDED", saved.getId());

        return mapToDTO(saved);
    }

    // =====================================
    // GET NEXT PATIENT
    // =====================================

    @Transactional
    public PatientResponseDTO getNextPatient(
            Long deptId
    ) {

        log.info(
                "Fetching next patient for dept: {}",
                deptId
        );

        List<Patient> list =
                patientRepository
                        .findNextPatientByDepartment(
                                deptId,
                                PageRequest.of(0, 1)
                        );

        if (list.isEmpty()) {
            return null;
        }

        Patient p = list.get(0);

        // IMPORTANT LIFECYCLE CHANGE

        p.setStatus(
                Status.IN_PROGRESS
        );

        Patient saved =
                patientRepository.save(p);

        return mapToDTO(saved);
    }

    // =====================================
    // FULL ACTIVE QUEUE
    // =====================================

    public List<PatientResponseDTO>
    getSortedQueue() {

        return patientRepository

                .findByStatusOrderByFinalSeverityDescArrivalTimeAsc(
                        Status.WAITING
                )

                .stream()

                .map(this::mapToDTO)

                .collect(Collectors.toList());
    }

    // =====================================
    // QUEUE BY DEPARTMENT
    // =====================================

    public List<PatientResponseDTO>
    getSortedQueue(Long deptId) {

        return patientRepository

                .findByDepartment_IdAndStatusOrderByFinalSeverityDescArrivalTimeAsc(
                        deptId,
                        Status.WAITING
                )

                .stream()

                .map(this::mapToDTO)

                .collect(Collectors.toList());
    }

    // =====================================
    // TREAT PATIENT
    // =====================================

    public PatientResponseDTO treatPatient(
            Long id
    ) {

        log.info(
                "Treating patient with id: {}",
                id
        );

        Patient p =
                patientRepository.findById(id)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Patient not found"
                                )
                        );

        p.setStatus(Status.TREATED);

        Patient saved =
                patientRepository.save(p);

        log("TREATED", id);

        return mapToDTO(saved);
    }

    // =====================================
    // OVERRIDE PRIORITY
    // =====================================

    public PatientResponseDTO
    overridePriority(
            Long id,
            int severity
    ) {

        log.info(
                "Overriding severity for patient: {}",
                id
        );

        if (severity < 1 || severity > 10) {

            throw new RuntimeException(
                    "Invalid severity (1-10 allowed)"
            );
        }

        Patient p =
                patientRepository.findById(id)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Patient not found"
                                )
                        );

        p.setFinalSeverity(severity);

        Patient saved =
                patientRepository.save(p);

        log("OVERRIDE", id);

        return mapToDTO(saved);
    }

    // =====================================
    // CRITICAL PATIENTS
    // =====================================

    public List<PatientResponseDTO>
    getCriticalPatients() {

        return patientRepository

                .findCriticalPatients()

                .stream()

                .map(this::mapToDTO)

                .collect(Collectors.toList());
    }

    // =====================================
    // LONG WAITING PATIENTS
    // =====================================

    public List<PatientResponseDTO>
    getLongWaitingPatients() {

        return patientRepository

                .findWaitingPatientsOrdered()

                .stream()

                .filter(p ->

                        Duration.between(
                                p.getArrivalTime(),
                                Instant.now()
                        ).toMinutes() > 30
                )

                .map(this::mapToDTO)

                .collect(Collectors.toList());
    }

    // =====================================
    // WAIT TIME PREDICTION
    // =====================================

    public Map<String, Object>
    predictWaitTime(Long id) {

        List<PatientResponseDTO> queue =
                getSortedQueue();

        int position = 1;

        for (PatientResponseDTO p : queue) {

            if (p.getId().equals(id)) {
                break;
            }

            position++;
        }

        int estimatedMinutes =
                position * 10;

        Map<String, Object> result =
                new HashMap<>();

        result.put("position", position);

        result.put(
                "estimatedWaitTimeMinutes",
                estimatedMinutes
        );

        return result;
    }

    // =====================================
    // DEPARTMENT LOAD
    // =====================================

    public Map<String, Long>
    getDepartmentLoad() {

        return patientRepository

                .findByStatusOrderByFinalSeverityDescArrivalTimeAsc(
                        Status.WAITING
                )

                .stream()

                .filter(p ->
                        p.getDepartment() != null
                )

                .collect(
                        Collectors.groupingBy(
                                p ->
                                        p.getDepartment()
                                                .getName(),
                                Collectors.counting()
                        )
                );
    }

    // =====================================
    // ANALYTICS
    // =====================================

    public Map<String, Object>
    getAnalytics() {

        List<Patient> all =
                patientRepository.findAll();

        long treated = all.stream()

                .filter(p ->
                        p.getStatus()
                                == Status.TREATED
                )

                .count();

        long waiting = all.stream()

                .filter(p ->
                        p.getStatus()
                                == Status.WAITING
                )

                .count();

        double avgWait = all.stream()

                .filter(p ->
                        p.getStatus()
                                == Status.TREATED
                )

                .mapToLong(p ->

                        Duration.between(
                                p.getArrivalTime(),
                                Instant.now()
                        ).toMinutes()
                )

                .average()

                .orElse(0);

        Map<String, Object> map =
                new HashMap<>();

        map.put(
                "treatedPatients",
                treated
        );

        map.put(
                "waitingPatients",
                waiting
        );

        map.put(
                "avgWaitTime",
                avgWait
        );

        return map;
    }

    // =====================================
    // QUEUE INSIGHTS
    // =====================================

    public Map<String, Object>
    getQueueInsights() {

        List<PatientResponseDTO> queue =
                getSortedQueue();

        Map<String, Object> map =
                new HashMap<>();

        map.put(
                "totalWaiting",
                queue.size()
        );

        map.put(
                "highestSeverity",

                queue.stream()

                        .mapToInt(
                                PatientResponseDTO::getSeverity
                        )

                        .max()

                        .orElse(0)
        );

        return map;
    }

    // =====================================
    // LOGGER
    // =====================================

    private void log(
            String action,
            Long patientId
    ) {

        PatientLog log =
                new PatientLog();

        log.setAction(action);

        log.setPatientId(patientId);

        // KEEP THIS IF PatientLog
        // STILL USES LocalDateTime

        log.setTimestamp(
                java.time.LocalDateTime.now()
        );

        logRepository.save(log);
    }
}