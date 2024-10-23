package com.sora.ryokka.service.impl;

import com.sora.ryokka.dto.request.CreateResourceRequest;
import com.sora.ryokka.exception.ResourceNotFoundException;
import com.sora.ryokka.model.Project;
import com.sora.ryokka.model.Resource;
import com.sora.ryokka.repository.ProjectRepository;
import com.sora.ryokka.repository.ResourceRepository;
import com.sora.ryokka.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ProjectRepository projectRepository; // Add ProjectRepository to fetch projects

    public ResourceServiceImpl(ResourceRepository resourceRepository, ProjectRepository projectRepository) {
        this.resourceRepository = resourceRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Resource createResource(CreateResourceRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + request.getProjectId()));

        Resource resource = new Resource();
        resource.setResourceName(request.getResourceName());
        resource.setResourceDescription(request.getResourceDescription());
        resource.setResourceType(request.getResourceType());
        resource.setUnitOfMeasure(request.getUnitOfMeasure());
        resource.setUnitCost(request.getUnitCost());
        resource.setQuantity(request.getQuantity());
        resource.setGeneralPrice(request.getGeneralPrice());
        resource.setProject(project);  // Set the associated project
        return resourceRepository.save(resource);
    }

    @Override
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    @Override
    public Optional<Resource> getResourceById(Long resourceId) {
        return resourceRepository.findById(resourceId);
    }

    @Override
    public List<Resource> getResourcesByProjectId(Long projectId) {
        return resourceRepository.findByProjectProjectId(projectId);
    }

    @Override
    public Resource updateResource(Long resourceId, CreateResourceRequest request) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + resourceId));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + request.getProjectId()));

        resource.setResourceName(request.getResourceName());
        resource.setResourceDescription(request.getResourceDescription());
        resource.setResourceType(request.getResourceType());
        resource.setUnitOfMeasure(request.getUnitOfMeasure());
        resource.setUnitCost(request.getUnitCost());
        resource.setQuantity(request.getQuantity());
        resource.setGeneralPrice(request.getGeneralPrice());
        resource.setProject(project);  // Update the associated project
        return resourceRepository.save(resource);
    }

    @Override
    public void deleteResource(Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + resourceId));
        resourceRepository.delete(resource);
    }
}
