package com.sora.ryokka.repository;

import com.sora.ryokka.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // Custom query methods can be defined here if needed
}
