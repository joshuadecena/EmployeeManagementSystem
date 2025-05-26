package com.capstonetwo.ems;

import com.capstonetwo.ems.model.Employee;
import com.capstonetwo.ems.model.Department;
import com.capstonetwo.ems.repository.EmployeeRepository;
import com.capstonetwo.ems.service.EmployeeServiceImpl;
import com.capstonetwo.ems.repository.DepartmentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private DepartmentRepository departmentRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetById_ExistingEmployee_ReturnsEmployee() {
		Employee emp = new Employee();
		emp.setId(1L);
		emp.setName("John Doe");

		when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));

		Employee result = employeeService.getById(1L);

		assertNotNull(result);
		assertEquals("John Doe", result.getName());

		verify(employeeRepository, times(1)).findById(1L);
	}

	@Test
	void testGetById_NonExistingEmployee_ThrowsException() {
		when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

		RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
			employeeService.getById(1L);
		});

		assertTrue(thrown.getMessage().contains("Employee not found with ID: 1"));
	}

	@Test
	void testSave_WithExistingDepartment() {
		Department dept = new Department();
		dept.setName("IT");

		Employee emp = new Employee();
		emp.setName("Jane");
		emp.setDepartment(dept);

		when(departmentRepository.findByName("IT")).thenReturn(Optional.of(dept));
		when(employeeRepository.save(emp)).thenReturn(emp);

		Employee saved = employeeService.save(emp);

		assertNotNull(saved);
		assertEquals("Jane", saved.getName());
		assertEquals("IT", saved.getDepartment().getName());

		verify(departmentRepository, times(1)).findByName("IT");
		verify(employeeRepository, times(1)).save(emp);
	}

	@Test
	void testSave_WithNewDepartment_CreatesDepartment() {
		Department dept = new Department();
		dept.setName("Finance");

		Employee emp = new Employee();
		emp.setName("Mark");
		emp.setDepartment(dept);

		when(departmentRepository.findByName("Finance")).thenReturn(Optional.empty());
		when(departmentRepository.save(any(Department.class))).thenAnswer(i -> i.getArgument(0));
		when(employeeRepository.save(emp)).thenReturn(emp);

		Employee saved = employeeService.save(emp);

		assertNotNull(saved);
		assertEquals("Mark", saved.getName());
		assertEquals("Finance", saved.getDepartment().getName());

		verify(departmentRepository, times(1)).findByName("Finance");
		verify(departmentRepository, times(1)).save(any(Department.class));
		verify(employeeRepository, times(1)).save(emp);
	}
	@Test
	void testUpdate_ExistingEmployee_UpdatesAndReturnsEmployee() {
	    Long empId = 1L;

	    Department oldDept = new Department();
	    oldDept.setName("HR");

	    Employee existingEmp = new Employee();
	    existingEmp.setId(empId);
	    existingEmp.setName("Old Name");
	    existingEmp.setDepartment(oldDept);

	    Department newDept = new Department();
	    newDept.setName("IT");

	    Employee newData = new Employee();
	    newData.setName("New Name");
	    newData.setDateOfBirth(LocalDate.of(1990, 1, 1));
	    newData.setSalary(Double.valueOf(50000));
	    newData.setDepartment(newDept);

	    when(employeeRepository.findById(empId)).thenReturn(Optional.of(existingEmp));
	    when(departmentRepository.findByName("IT")).thenReturn(Optional.of(newDept));
	    when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> i.getArgument(0));

	    Employee updated = employeeService.update(empId, newData);

	    assertNotNull(updated);
	    assertEquals("New Name", updated.getName());
	    assertEquals(LocalDate.of(1990, 1, 1), updated.getDateOfBirth());
	    assertEquals(50000, updated.getSalary());
	    assertEquals("IT", updated.getDepartment().getName());

	    verify(employeeRepository, times(1)).findById(empId);
	    verify(departmentRepository, times(1)).findByName("IT");
	    verify(employeeRepository, times(1)).save(existingEmp);
	}

	@Test
	void testUpdate_NonExistingEmployee_ThrowsException() {
	    Long empId = 1L;
	    Employee newData = new Employee();

	    when(employeeRepository.findById(empId)).thenReturn(Optional.empty());

	    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
	        employeeService.update(empId, newData);
	    });

	    assertTrue(thrown.getMessage().contains("Employee not found with ID: " + empId));

	    verify(employeeRepository, times(1)).findById(empId);
	    verify(employeeRepository, never()).save(any(Employee.class));
	}

	@Test
	void testDelete_VerifyRepositoryCalled() {
	    Long empId = 1L;

	    doNothing().when(employeeRepository).deleteById(empId);

	    employeeService.delete(empId);

	    verify(employeeRepository, times(1)).deleteById(empId);
	}
	
	@Test
	void testGetAverageSalary_ReturnsCorrectValue() {
		List<Employee> employees = Arrays.asList(
			new Employee() {{ setSalary(50000.0); }},
			new Employee() {{ setSalary(60000.0); }},
			new Employee() {{ setSalary(70000.0); }}
		);

		when(employeeRepository.findAll()).thenReturn(employees);

		double avgSalary = employeeService.getAverageSalary();

		assertEquals(60000.0, avgSalary, 0.001);
		verify(employeeRepository, times(1)).findAll();

		}
}
