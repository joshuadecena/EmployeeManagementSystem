package com.capstonetwo.ems.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	public Department() {
		
	}
	
	public Department(String name) {
		this.name = name;
	}
	
	//If you want to see all employees in a department
	@OneToMany(mappedBy = "department")
	private List<Employee> employees;
	
	public Long getId() { return id; }
	public void setId(long id) {this.id = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
