package com.capstonetwo.ems.controller;

import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@GetMapping
	public List<Department> getAll() {
		return departmentService.getAll();
	}

	@GetMapping("/{id}")
	public Department getById(@PathVariable Long id) {
		return departmentService.getById(id);
	}

	// Removing this because I will not add, edit, or delete departments
	// @PostMapping
	// @PutMapping
	// @DeleteMapping
}
