package com.sora.ryokka.service;

import com.sora.ryokka.model.Project;
import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getAllProjects();
    Optional<Project> getProjectById(int id);
    Project createProject(Project project);
    Project updateProject(int id, Project project);
    void deleteProject(int id);
}
