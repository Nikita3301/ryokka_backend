package com.sora.ryokka.repository;

import com.sora.ryokka.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    // Custom query methods can be defined here if needed
}
