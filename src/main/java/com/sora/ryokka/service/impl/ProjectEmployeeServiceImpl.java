package com.sora.ryokka.service.impl;

import com.sora.ryokka.exception.ResourceNotFoundException;
import com.sora.ryokka.model.Employee;
import com.sora.ryokka.model.Project;
import com.sora.ryokka.model.ProjectEmployee;
import com.sora.ryokka.model.ProjectEmployeeId;
import com.sora.ryokka.repository.EmployeeRepository;
import com.sora.ryokka.repository.ProjectEmployeeRepository;
import com.sora.ryokka.repository.ProjectRepository;
import com.sora.ryokka.service.ProjectEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectEmployeeServiceImpl implements ProjectEmployeeService {

    private final ProjectEmployeeRepository projectEmployeeRepository;

    private final EmployeeRepository employeeRepository;

    private final ProjectRepository projectRepository;

    public ProjectEmployeeServiceImpl(ProjectEmployeeRepository projectEmployeeRepository, EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.projectEmployeeRepository = projectEmployeeRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void assignEmployeeToProject(Long employeeId, Long projectId, Date startDate, Date endDate, String role) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        System.out.println("Employee ID: " + employee.getEmployeeId());

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        ProjectEmployeeId projectEmployeeId = new ProjectEmployeeId(projectId, employeeId);


        System.out.println("Project ID: " + project.getProjectId());

        ProjectEmployee projectEmployee = new ProjectEmployee();
        projectEmployee.setId(projectEmployeeId);
        projectEmployee.setEmployee(employee);
        projectEmployee.setProject(project);
        projectEmployee.setStartDate(startDate);
        projectEmployee.setEndDate(endDate);
        projectEmployee.setProjectRole(role);

        // Print projectEmployee details
        System.out.println("Assigning Employee to Project:");
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Role: " + role);

        projectEmployeeRepository.save(projectEmployee);

        System.out.println("Employee assigned to project successfully.");
    }

    @Override
    public void removeEmployeeFromProject(Long employeeId, Long projectId) {
        ProjectEmployeeId projectEmployeeId = new ProjectEmployeeId();
        projectEmployeeId.setEmployeeId(employeeId);
        projectEmployeeId.setProjectId(projectId);

        projectEmployeeRepository.deleteById(projectEmployeeId);
    }

    @Override
    public List<Employee> getEmployeesByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        List<ProjectEmployee> projectEmployees = projectEmployeeRepository.findByProject(project);

        return projectEmployees.stream()
                .map(ProjectEmployee::getEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public List<Project> getProjectsByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        List<ProjectEmployee> projectEmployees = projectEmployeeRepository.findByEmployee(employee);

        return projectEmployees.stream()
                .map(ProjectEmployee::getProject)
                .collect(Collectors.toList());
    }
}

