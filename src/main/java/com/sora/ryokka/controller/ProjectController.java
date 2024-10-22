package com.sora.ryokka.controller;

import com.sora.ryokka.dto.request.CreateProjectRequest;
import com.sora.ryokka.dto.response.DetailsProjectDataResponse;
import com.sora.ryokka.dto.response.ProjectDataResponse;
import com.sora.ryokka.exception.ResourceNotFoundException;
import com.sora.ryokka.model.Project;
import com.sora.ryokka.service.ProjectService;
import jakarta.validation.Valid;
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
        List<ProjectDataResponse> projectDataResponse = new ArrayList<>();

        for (Project project : projects) {
            ProjectDataResponse dto = new ProjectDataResponse(project);
            projectDataResponse.add(dto);
        }

        return ResponseEntity.ok().body(projectDataResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailsProjectDataResponse> getProjectById(@PathVariable Long id) {
        Optional<Project> optionalProject = projectService.getProjectById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            return ResponseEntity.ok().body(new DetailsProjectDataResponse(project));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DetailsProjectDataResponse> createProject(@RequestBody CreateProjectRequest projectRequest) {
        DetailsProjectDataResponse createdProjectDTO = projectService.createProject(projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProjectDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DetailsProjectDataResponse> updateProject(@PathVariable Long id, @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateProject(id, project);
            DetailsProjectDataResponse dto = new DetailsProjectDataResponse(updatedProject);
            return ResponseEntity.ok().body(dto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
