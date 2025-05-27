package com.capstonetwo.ems.service;

import com.capstonetwo.ems.model.Employee;
import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.repository.EmployeeRepository;
import com.capstonetwo.ems.repository.DepartmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.time.LocalDate;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // GET all employees
    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    // GET employee by ID
    @Override
    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    // Search employees with filters
    @Override
    public List<Employee> searchEmployees(Long id, String name, String department, Integer minAge) {
        LocalDate minDateOfBirth = (minAge != null) ? LocalDate.now().minusYears(minAge) : null;
        return employeeRepository.searchEmployees(id, name, department, minDateOfBirth);
    }

    // GET employees by department name
    @Override
    public List<Employee> findByDepartment(String name) {
        return employeeRepository.findAll().stream()
                .filter(e -> e.getDepartment() != null && e.getDepartment().getName().equalsIgnoreCase(name))
                .toList();
    }

    // SAVE new employee
    @Override
    public Employee save(Employee emp) {
        if (emp.getDepartment() != null) {
            String deptName = emp.getDepartment().getName();
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

    // UPDATE existing employee
    @Override
    public Employee update(Long id, Employee newData) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        emp.setName(newData.getName());
        emp.setDateOfBirth(newData.getDateOfBirth());
        emp.setSalary(newData.getSalary());

        if (newData.getDepartment() != null) {
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

    // DELETE employee by ID
    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    // AVERAGE salary
    @Override
    public double getAverageSalary() {
        return employeeRepository.findAll().stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
    }

    // AVERAGE age
    @Override
    public double getAverageAge() {
        return employeeRepository.findAll().stream()
                .mapToInt(Employee::getAge)
                .average()
                .orElse(0);
    }
}
