package com.capstonetwo.ems.controller;

import com.capstonetwo.ems.model.Employee;
//import com.capstonetwo.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.capstonetwo.ems.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@GetMapping

	public List<Employee> getAll() {
		return employeeService.getAll();
	}

	@PostMapping
	public Employee create(@RequestBody Employee e) {
		return employeeService.save(e);
	}

	@PutMapping("/{id}")
	public Employee update(@PathVariable Long id, @RequestBody Employee e) {
		return employeeService.update(id, e);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		employeeService.delete(id);
	}

	@GetMapping("/average-salary")
	public double avgSalary() {
		return employeeService.getAverageSalary();
	}

	@GetMapping("/average-age")
	public double avgAge() {
		return employeeService.getAverageAge();
	}

}
