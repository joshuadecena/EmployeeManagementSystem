package com.capstonetwo.ems.controller;

import com.capstonetwo.ems.model.Employee;
import com.capstonetwo.ems.service.EmployeeService;
import com.capstonetwo.ems.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.text.NumberFormat;
//import java.util.Locale;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAll();
        return ResponseEntity.ok(employees);
    }

    // Search employees with optional filters
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Integer minAge
    ) {
        List<Employee> results = employeeService.searchEmployees(id, name, department, minAge);
        return ResponseEntity.ok(results);
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    // Create new employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee created = employeeService.save(employee);
        return ResponseEntity.ok(created);
    }

    // Update employee by ID
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee
    ) {
        Employee updated = employeeService.update(id, employee);
        return ResponseEntity.ok(updated);
    }

    // Delete employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Get average salary
    @GetMapping("/average-salary")
    public ResponseEntity<Double> getAverageSalary() {
        double avgSalary = employeeService.getAverageSalary();
        return ResponseEntity.ok(avgSalary);
    }

    // Get average age
    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageAge() {
        double avgAge = employeeService.getAverageAge();
        return ResponseEntity.ok(avgAge);
    }
}
