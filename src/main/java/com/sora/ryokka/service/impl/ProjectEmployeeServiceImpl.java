package com.sora.ryokka.service.impl;

import com.sora.ryokka.dto.request.ProjectEmployeeRequest;
import com.sora.ryokka.model.Employee;
import com.sora.ryokka.model.Project;
import com.sora.ryokka.model.ProjectEmployee;
import com.sora.ryokka.repository.EmployeeRepository;
import com.sora.ryokka.repository.ProjectEmployeeRepository;
import com.sora.ryokka.repository.ProjectRepository;
import com.sora.ryokka.service.ProjectEmployeeService;
import org.springframework.stereotype.Service;

@Service
public class ProjectEmployeeServiceImpl implements ProjectEmployeeService {

    private final ProjectEmployeeRepository projectEmployeeRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    public ProjectEmployeeServiceImpl(ProjectEmployeeRepository projectEmployeeRepository,
                                      EmployeeRepository employeeRepository,
                                      ProjectRepository projectRepository) {
        this.projectEmployeeRepository = projectEmployeeRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void addEmployeeToProject(ProjectEmployeeRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID"));

        ProjectEmployee projectEmployee = new ProjectEmployee();
        projectEmployee.setEmployee(employee);
        projectEmployee.setProject(project);
        projectEmployee.setStartDate(request.getStartDate());
        projectEmployee.setEndDate(request.getEndDate());
        projectEmployee.setProjectRole(request.getProjectRole());

        projectEmployeeRepository.save(projectEmployee);
    }

    @Override
    public void removeEmployeeFromProject(ProjectEmployeeRequest request) {
        ProjectEmployee projectEmployee = projectEmployeeRepository.findByEmployeeEmployeeIdAndProjectProjectId(
                        request.getEmployeeId(), request.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not assigned to project"));

        projectEmployeeRepository.delete(projectEmployee);
    }
}
