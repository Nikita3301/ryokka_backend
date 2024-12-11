package com.sora.ryokka.service.impl;

import com.sora.ryokka.dto.request.CreateProjectRequest;
import com.sora.ryokka.dto.response.DetailsProjectDataResponse;
import com.sora.ryokka.model.Client;
import com.sora.ryokka.model.Project;
import com.sora.ryokka.model.ProjectStatus;
import com.sora.ryokka.repository.ClientRepository;
import com.sora.ryokka.repository.ProjectRepository;
import com.sora.ryokka.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {


    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ClientRepository clientRepository) {
        this.projectRepository = projectRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public DetailsProjectDataResponse createProject(CreateProjectRequest projectRequest) {
        Client client = clientRepository.findById(projectRequest.clientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + projectRequest.clientId()));

        Project project = new Project();
        project.setProjectName(projectRequest.projectName());
        project.setProjectDescription(projectRequest.projectDescription());
        project.setProjectLocation(projectRequest.projectLocation());
        project.setProjectStatus(ProjectStatus.fromDescription(projectRequest.projectStatus()));
        project.setStartDate(projectRequest.startDate());
        project.setEndDate(projectRequest.endDate());
        project.setProjectBudget(projectRequest.projectBudget());
        project.setClient(client);

        Project createdProject = projectRepository.save(project);

        return new DetailsProjectDataResponse(createdProject);
    }


    @Override
    public Project updateProject(Long projectId, Project updatedProject) {
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
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
