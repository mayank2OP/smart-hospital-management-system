package com.project.erqueue.config;

import com.project.erqueue.model.Department;
import com.project.erqueue.repository.DepartmentRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDepartments(
            DepartmentRepository repo
    ) {

        return args -> {

            if (repo.count() == 0) {

                String[] departments = {

                        "Emergency",
                        "Cardiology",
                        "Neurology",
                        "Orthopedics",
                        "Pediatrics",
                        "Dermatology",
                        "Radiology",
                        "ICU",
                        "General Surgery",
                        "ENT",
                        "Oncology",
                        "Psychiatry"
                };

                for (String name : departments) {

                    Department dept =
                            new Department();

                    dept.setName(name);

                    repo.save(dept);
                }

                System.out.println(
                        "Departments seeded successfully."
                );
            }
        };
    }
}