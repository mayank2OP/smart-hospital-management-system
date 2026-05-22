package com.project.erqueue.controller;

import com.project.erqueue.model.Department;
import com.project.erqueue.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository repo;

    @GetMapping
    public List<Department> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Department add(@RequestBody Department d) {
        return repo.save(d);
    }
}