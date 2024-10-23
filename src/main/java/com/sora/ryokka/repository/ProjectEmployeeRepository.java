package com.sora.ryokka.repository;

import com.sora.ryokka.model.Employee;
import com.sora.ryokka.model.Project;
import com.sora.ryokka.model.ProjectEmployee;
import com.sora.ryokka.model.ProjectEmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, ProjectEmployeeId> {
    List<ProjectEmployee> findByProject(Project project);
    List<ProjectEmployee> findByEmployee(Employee employee);
}

