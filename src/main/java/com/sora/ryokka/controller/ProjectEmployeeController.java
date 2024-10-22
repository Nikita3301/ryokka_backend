package com.sora.ryokka.controller;

import com.sora.ryokka.dto.request.ProjectEmployeeRequest;
import com.sora.ryokka.service.ProjectEmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project-employees")
public class ProjectEmployeeController {

    private final ProjectEmployeeService projectEmployeeService;

    public ProjectEmployeeController(ProjectEmployeeService projectEmployeeService) {
        this.projectEmployeeService = projectEmployeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addEmployeeToProject(@RequestBody ProjectEmployeeRequest request) {
        projectEmployeeService.addEmployeeToProject(request);
        return ResponseEntity.ok("Employee added to project");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeEmployeeFromProject(@RequestBody ProjectEmployeeRequest request) {
        projectEmployeeService.removeEmployeeFromProject(request);
        return ResponseEntity.ok("Employee removed from project");
    }
}
