package com.sora.ryokka.service;

import com.sora.ryokka.dto.request.CreateProjectRequest;
import com.sora.ryokka.dto.response.DetailsProjectDataResponse;
import com.sora.ryokka.model.ProjectImage;
import com.sora.ryokka.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getAllProjects();

    Optional<Project> getProjectById(Long projectId);


    DetailsProjectDataResponse createProject(CreateProjectRequest projectRequest);

    Project updateProject(Long projectId, Project updatedProject);

    void deleteProject(Long projectId);
}
