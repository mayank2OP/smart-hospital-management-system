package com.project.erqueue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.erqueue.model.PriorityConfig;

public interface PriorityConfigRepository 
        extends JpaRepository<PriorityConfig, Long> {

    PriorityConfig findTopByOrderByIdDesc(); // latest config
}