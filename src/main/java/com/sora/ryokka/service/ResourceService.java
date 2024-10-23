package com.sora.ryokka.service;

import com.sora.ryokka.dto.request.CreateResourceRequest;
import com.sora.ryokka.model.Resource;
import java.util.List;
import java.util.Optional;

public interface ResourceService {

    // Create a new resource
    Resource createResource(CreateResourceRequest request);

    // Retrieve all resources
    List<Resource> getAllResources();

    // Retrieve a resource by ID
    Optional<Resource> getResourceById(Long resourceId);

    // Retrieve resources by project ID
    List<Resource> getResourcesByProjectId(Long projectId);

    // Update an existing resource
    Resource updateResource(Long resourceId, CreateResourceRequest request);

    // Delete a resource
    void deleteResource(Long resourceId);
}
