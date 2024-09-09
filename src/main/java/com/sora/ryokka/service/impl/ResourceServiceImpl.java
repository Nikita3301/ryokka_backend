package com.sora.ryokka.service.impl;

import com.sora.ryokka.model.Resource;
import com.sora.ryokka.repository.ResourceRepository;
import com.sora.ryokka.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    @Override
    public Optional<Resource> getResourceById(int resourceId) {
        return resourceRepository.findById(resourceId);
    }

    @Override
    public Resource createResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public Resource updateResource(int resourceId, Resource updatedResource) {
        return resourceRepository.findById(resourceId)
                .map(resource -> {
                    resource.setResourceName(updatedResource.getResourceName());
                    resource.setResourceDescription(updatedResource.getResourceDescription());
                    resource.setResourceType(updatedResource.getResourceType());
                    resource.setUnitOfMeasure(updatedResource.getUnitOfMeasure());
                    resource.setUnitCost(updatedResource.getUnitCost());
                    resource.setQuantity(updatedResource.getQuantity());
                    resource.setGeneralPrice(updatedResource.getGeneralPrice());
                    return resourceRepository.save(resource);
                })
                .orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    @Override
    public void deleteResource(int resourceId) {
        resourceRepository.deleteById(resourceId);
    }
}
