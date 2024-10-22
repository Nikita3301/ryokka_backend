package com.sora.ryokka.repository;

import com.sora.ryokka.model.ProjectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, Long> {
    Optional<ProjectEmployee> findByEmployeeEmployeeIdAndProjectProjectId(Long employeeId, Long projectId);
}
