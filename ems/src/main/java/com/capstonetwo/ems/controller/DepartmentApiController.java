package com.capstonetwo.ems.controller;

import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.service.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${department.api.base}")  // e.g. "/api/departments"
public class DepartmentApiController {

    @Autowired
    private DepartmentService departmentService;

    // Get all departments as JSON
    @GetMapping
    public List<Department> getAll() {
        return departmentService.getAll();
    }

    // Get department by ID as JSON
    @GetMapping("/{id}")
    public Department getById(@PathVariable Long id) {
        return departmentService.getById(id);
    }

    // Create a new department via JSON REST API
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department created = departmentService.save(department);
        return ResponseEntity.ok(created);
    }

    // Update an existing department via JSON REST API
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        Department updated = departmentService.update(id, department);
        return ResponseEntity.ok(updated);
    }

    // Delete a department via JSON REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
}
