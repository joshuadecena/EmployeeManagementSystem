package com.capstonetwo.ems.repository;

import com.capstonetwo.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE " +
           "(:id IS NULL OR e.id = :id) AND " +
           "(:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:department IS NULL OR LOWER(e.department.name) = LOWER(:department)) AND " +
           "(:minDateOfBirth IS NULL OR e.dateOfBirth <= :minDateOfBirth)")
    List<Employee> searchEmployees(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("department") String department,
            @Param("minDateOfBirth") LocalDate minDateOfBirth);

}
// This interface extends JpaRepository to provide CRUD operations for Employee entities.
// It includes a custom query method to search employees based on various filters such as ID, name, department, and minimum date of birth.
// The searchEmployees method uses JPQL to filter results based on the provided parameters, allowing for flexible searching.
// 