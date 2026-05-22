package com.project.erqueue.controller;

import com.project.erqueue.model.PriorityConfig;
import com.project.erqueue.repository.PriorityConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/config")
public class PriorityConfigController {

    @Autowired
    private PriorityConfigRepository repo;

    // ✅ Add config
    @PostMapping
    public PriorityConfig addConfig(@RequestBody PriorityConfig config) {
        return repo.save(config);
    }

    // ✅ View configs
    @GetMapping
    public List<PriorityConfig> getConfigs() {
        return repo.findAll();
    }
}