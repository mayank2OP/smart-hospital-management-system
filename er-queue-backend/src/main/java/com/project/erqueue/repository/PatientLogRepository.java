package com.project.erqueue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.erqueue.model.PatientLog;

public interface PatientLogRepository
        extends JpaRepository<PatientLog, Long> {

    List<PatientLog>
        findTop10ByOrderByTimestampDesc();
}