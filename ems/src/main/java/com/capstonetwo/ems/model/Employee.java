package com.capstonetwo.ems.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Employee extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double salary;

    @JsonIgnoreProperties("employees")  // Prevent infinite recursion with Department.employees
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}
