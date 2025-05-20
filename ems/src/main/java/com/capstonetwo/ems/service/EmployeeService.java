package com.capstonetwo.ems.service;

import com.capstonetwo.ems.model.Employee;

import java.util.List;

public interface EmployeeService {
	List<Employee> getAll();
	Employee getById(Long id);
	Employee save(Employee emp);
	Employee update(Long id, Employee newData);
	void delete(Long id);
	double getAverageSalary();
	double getAverageAge();
	List<Employee> findByDepartment(String name);
}