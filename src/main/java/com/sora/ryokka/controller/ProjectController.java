package com.sora.ryokka.controller;

import com.sora.ryokka.dto.request.CreateProjectRequest;
import com.sora.ryokka.dto.response.DetailsProjectDataResponse;
import com.sora.ryokka.dto.response.ProjectDataResponse;
import com.sora.ryokka.exception.ResourceNotFoundException;
import com.sora.ryokka.model.Project;
import com.sora.ryokka.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
        List<ProjectDataResponse> projectDataResponse = new ArrayList<>();

        for (Project project : projects) {
            projectDataResponse.add(new ProjectDataResponse(project));
        }

        return ResponseEntity.ok().body(projectDataResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailsProjectDataResponse> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));
        return ResponseEntity.ok().body(new DetailsProjectDataResponse(project));
    }

    @PostMapping
    public ResponseEntity<DetailsProjectDataResponse> createProject(@RequestBody CreateProjectRequest projectRequest) {
        DetailsProjectDataResponse createdProjectDTO = projectService.createProject(projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProjectDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailsProjectDataResponse> updateProject(@PathVariable Long id, @RequestBody Project project) {
        Project updatedProject = projectService.updateProject(id, project);
        DetailsProjectDataResponse dto = new DetailsProjectDataResponse(updatedProject);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
