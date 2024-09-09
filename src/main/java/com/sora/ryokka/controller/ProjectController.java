package com.sora.ryokka.controller;

import com.sora.ryokka.dto.response.ProjectDataResponse;
import com.sora.ryokka.exception.ResourceNotFoundException;
import com.sora.ryokka.model.Project;
import com.sora.ryokka.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectDataResponse>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectDataResponse> projectDataResponses = new ArrayList<>();

        for (Project project : projects) {
            ProjectDataResponse dto = new ProjectDataResponse(project);
            projectDataResponses.add(dto);
        }

        return ResponseEntity.ok().body(projectDataResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDataResponse> getProjectById(@PathVariable Integer id) {
        Optional<Project> optionalProject = projectService.getProjectById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            return ResponseEntity.ok().body(new ProjectDataResponse(project));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<ProjectDataResponse> createProject(@RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
        ProjectDataResponse dto = new ProjectDataResponse(createdProject);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProjectDataResponse> updateProject(@PathVariable Integer id, @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateProject(id, project);
            ProjectDataResponse dto = new ProjectDataResponse(updatedProject);
            return ResponseEntity.ok().body(dto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
