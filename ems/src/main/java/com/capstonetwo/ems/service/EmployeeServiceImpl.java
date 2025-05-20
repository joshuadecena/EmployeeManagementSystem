package com.capstonetwo.ems.service;

import com.capstonetwo.ems.model.Employee;
import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.repository.EmployeeRepository;
import com.capstonetwo.ems.repository.DepartmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	//GET All Employees
	@Override
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}
	
	//GET Employees by ID
	@Override
	public Employee getById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
	}
	
	//GET All Employees By Department
	@Override
	public List<Employee> findByDepartment(String name) {
		return employeeRepository.findAll().stream().filter(e -> e.getDepartment().getName().equalsIgnoreCase(name))
				.toList();
	}

	//POST New Employee
	@Override
	public Employee save(Employee emp) {
		// Department from name
		if (emp.getDepartment() != null) {
			String deptName = emp.getDepartment().getName();
			Department department = departmentRepository.findByName(deptName).orElseGet(() -> {
				Department newDept = new Department();
				newDept.setName(deptName);
				return departmentRepository.save(newDept);
			});
			emp.setDepartment(department);
		}
		return employeeRepository.save(emp);
	}
	
	//PUT Employee
	@Override
	public Employee update(Long id, Employee newData) {
		Employee emp = employeeRepository.findById(id).orElseThrow();
		
		emp.setName(newData.getName());
		emp.setDateOfBirth(newData.getDateOfBirth());
		emp.setSalary(newData.getSalary());
		
		if (newData.getDepartment() != null ) {
			String deptName = newData.getDepartment().getName();
			Department department = departmentRepository.findByName(deptName)
					.orElseGet(() -> {
						Department newDept = new Department();
						newDept.setName(deptName);
						return departmentRepository.save(newDept);
					});
			emp.setDepartment(department);
		}
		
		return employeeRepository.save(emp);
	}

	//Delete Employee By ID
	@Override
	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public double getAverageSalary() {
		return employeeRepository.findAll().stream()
				.mapToDouble(Employee::getSalary).average().orElse(0);
	}
	
	@Override
	public double getAverageAge() {
		return employeeRepository.findAll().stream()
				.mapToInt(Employee::getAge).average().orElse(0);
	}

}