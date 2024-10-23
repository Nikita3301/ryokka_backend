package com.sora.ryokka.controller;

import com.sora.ryokka.dto.request.ProjectEmployeeRequest;
import com.sora.ryokka.model.Employee;
import com.sora.ryokka.model.Project;
import com.sora.ryokka.service.ProjectEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/project-employees")
public class ProjectEmployeeController {

    @Autowired
    private ProjectEmployeeService projectEmployeeService;

    @PostMapping("/assign")
    public ResponseEntity<String> assignEmployeeToProject(@RequestParam Long employeeId,
                                                          @RequestParam Long projectId,
                                                          @RequestParam Date startDate,
                                                          @RequestParam(required = false) Date endDate,
                                                          @RequestParam String role) {
        projectEmployeeService.assignEmployeeToProject(employeeId, projectId, startDate, endDate, role);
        return ResponseEntity.ok("Employee assigned to project successfully.");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeEmployeeFromProject(@RequestParam Long employeeId,
                                                            @RequestParam Long projectId) {
        projectEmployeeService.removeEmployeeFromProject(employeeId, projectId);
        return ResponseEntity.ok("Employee removed from project successfully.");
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployeesByProject(@RequestParam Long projectId) {
        return ResponseEntity.ok(projectEmployeeService.getEmployeesByProject(projectId));
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getProjectsByEmployee(@RequestParam Long employeeId) {
        return ResponseEntity.ok(projectEmployeeService.getProjectsByEmployee(employeeId));
    }
}
