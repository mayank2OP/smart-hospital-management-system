package com.project.erqueue.repository;

import com.project.erqueue.model.Patient;
import com.project.erqueue.model.Status;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

import java.util.List;

public interface PatientRepository
        extends JpaRepository<Patient, Long> {

    // =====================================
    // ACTIVE WAITING QUEUE BY DEPARTMENT
    // =====================================

    List<Patient>
    findByDepartment_IdAndStatusOrderByFinalSeverityDescArrivalTimeAsc(
            Long departmentId,
            Status status
    );

    // =====================================
    // ALL ACTIVE WAITING PATIENTS
    // =====================================

    List<Patient>
    findByStatusOrderByFinalSeverityDescArrivalTimeAsc(
            Status status
    );

    // =====================================
    // NEXT PATIENT GLOBAL WITH LOCK
    // =====================================

    @Lock(LockModeType.PESSIMISTIC_WRITE)

    @Query("""
            SELECT p
            FROM Patient p
            WHERE p.status =
                com.project.erqueue.model.Status.WAITING
            ORDER BY
                p.finalSeverity DESC,
                p.arrivalTime ASC
            """)
    List<Patient>
    findNextPatientWithLock(
            Pageable pageable
    );

    // =====================================
    // NEXT PATIENT BY DEPARTMENT
    // =====================================

    @Lock(LockModeType.PESSIMISTIC_WRITE)

    @Query("""
            SELECT p
            FROM Patient p
            WHERE p.status =
                com.project.erqueue.model.Status.WAITING
            AND p.department.id = :deptId
            ORDER BY
                p.finalSeverity DESC,
                p.arrivalTime ASC
            """)
    List<Patient>
    findNextPatientByDepartment(
            @Param("deptId")
            Long deptId,

            Pageable pageable
    );

    // =====================================
    // CRITICAL ACTIVE PATIENTS
    // =====================================

    @Query("""
            SELECT p
            FROM Patient p
            WHERE p.status =
                com.project.erqueue.model.Status.WAITING
            AND p.finalSeverity >= 8
            ORDER BY
                p.finalSeverity DESC,
                p.arrivalTime ASC
            """)
    List<Patient>
    findCriticalPatients();

    // =====================================
    // LONG WAITING ACTIVE PATIENTS
    // =====================================

    @Query("""
            SELECT p
            FROM Patient p
            WHERE p.status =
                com.project.erqueue.model.Status.WAITING
            ORDER BY p.arrivalTime ASC
            """)
    List<Patient>
    findWaitingPatientsOrdered();
}