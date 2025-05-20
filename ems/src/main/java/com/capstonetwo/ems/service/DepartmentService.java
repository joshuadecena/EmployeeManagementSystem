package com.capstonetwo.ems.service;

import com.capstonetwo.ems.model.Department;

import java.util.List;

public interface DepartmentService {
	List<Department> getAll();
	Department getById(Long id);
	Department save(Department department);
	Department update(Long id, Department department);
	void delete(Long id);
}
