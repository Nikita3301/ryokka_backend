package com.sora.ryokka.service;

import com.sora.ryokka.dto.request.ProjectEmployeeRequest;
import com.sora.ryokka.model.Employee;
import com.sora.ryokka.model.Project;

import java.sql.Date;
import java.util.List;

public interface ProjectEmployeeService {
    void assignEmployeeToProject(Long employeeId, Long projectId, Date startDate, Date endDate, String role);
    void removeEmployeeFromProject(Long employeeId, Long projectId);
    List<Employee> getEmployeesByProject(Long projectId);
    List<Project> getProjectsByEmployee(Long employeeId);
}
