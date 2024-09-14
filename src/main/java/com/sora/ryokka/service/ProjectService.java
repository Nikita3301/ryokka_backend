package com.sora.ryokka.service;

import com.sora.ryokka.model.ProjectImage;
import com.sora.ryokka.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getAllProjects();

    Optional<Project> getProjectById(int projectId);

    Project createProject(Project project);

    Project updateProject(int projectId, Project updatedProject);

    void deleteProject(int projectId);
}
