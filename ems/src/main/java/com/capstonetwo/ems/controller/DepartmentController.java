package com.capstonetwo.ems.controller;

import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.service.DepartmentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("${department.api.base}")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	@Value("${department.api.base}")
	private String departmentApiBase;

	@GetMapping
	public List<Department> getAll() {
		return departmentService.getAll();
	}

	@GetMapping("/{id}")
	public Department getById(@PathVariable Long id) {
		return departmentService.getById(id);
	}
	
	// Add new department
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department created = departmentService.save(department);
        return ResponseEntity.ok(created);
    }

    // Update department by ID
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable Long id,
            @RequestBody Department department
    ) {
        Department updated = departmentService.update(id, department);
        return ResponseEntity.ok(updated);
    }

    // Delete department by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
