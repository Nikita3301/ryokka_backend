package com.sora.ryokka.repository;

import com.sora.ryokka.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    // Custom query methods can be defined here if needed
}