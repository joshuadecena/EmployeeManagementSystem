package com.capstonetwo.ems.model;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.Period;

@MappedSuperclass
public abstract class Person {
	private String name;
	private LocalDate dateOfBirth;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public LocalDate getDateOfBirth() { return dateOfBirth; }
	public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
	
	public int getAge() {
	    if (dateOfBirth == null) {
	        return 0; // or some default value or throw a custom exception
	    }
	    return Period.between(dateOfBirth, LocalDate.now()).getYears();
	}

}
