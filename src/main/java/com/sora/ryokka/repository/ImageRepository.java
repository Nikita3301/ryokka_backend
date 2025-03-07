package com.sora.ryokka.repository;

import com.sora.ryokka.model.ProjectImage;
import com.sora.ryokka.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<ProjectImage, Long> {
    List<ProjectImage> findByProjectProjectId(Long project_projectId);

    ProjectImage findByProjectAndIsMainImageTrue(Project project);
}

