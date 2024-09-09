package com.sora.ryokka.service.impl;

import com.sora.ryokka.dto.response.ProjectDataResponse;
import com.sora.ryokka.model.Project;
import com.sora.ryokka.repository.ProjectRepository;
import com.sora.ryokka.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {


    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(int projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(int projectId, Project updatedProject) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    project.setProjectName(updatedProject.getProjectName());
                    project.setStartDate(updatedProject.getStartDate());
                    project.setEndDate(updatedProject.getEndDate());
                    project.setProjectStatus(updatedProject.getProjectStatus());
                    project.setProjectDescription(updatedProject.getProjectDescription());
                    project.setProjectBudget(updatedProject.getProjectBudget());
                    project.setProjectLocation(updatedProject.getProjectLocation());
                    return projectRepository.save(project);
                })
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public void deleteProject(int projectId) {
        projectRepository.deleteById(projectId);
    }
}
