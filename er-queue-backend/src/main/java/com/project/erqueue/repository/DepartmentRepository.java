package com.project.erqueue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.erqueue.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}