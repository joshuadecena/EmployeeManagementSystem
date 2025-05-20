package com.capstonetwo.ems.config;

import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.repository.DepartmentRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedDepartments(DepartmentRepository departmentRepository) {
        return args -> {
            if (departmentRepository.count() == 0) {
                departmentRepository.save(new Department("IT"));
                departmentRepository.save(new Department("HR"));
                departmentRepository.save(new Department("Finance"));
                departmentRepository.save(new Department("Marketing"));
                System.out.println("Predefined departments.");
            }
        };
    }
}
