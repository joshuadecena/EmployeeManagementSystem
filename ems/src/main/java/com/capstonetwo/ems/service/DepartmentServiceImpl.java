package com.capstonetwo.ems.service;

import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.repository.DepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public List<Department> getAll() {
		return departmentRepository.findAll();
	}

	@Override
	public Department getById(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
	}

	@Override
	public Department save(Department department) {
		return departmentRepository.save(department);
	}

	@Override
	public Department update(Long id, Department department) {
		Department existing = departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
		existing.setName(department.getName());
		return departmentRepository.save(existing);
	}

	@Override
	public void delete(Long id) {
		departmentRepository.deleteById(id);
	}
}
