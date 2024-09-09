package com.sora.ryokka.service;

import com.sora.ryokka.model.Resource;
import java.util.List;
import java.util.Optional;

public interface ResourceService {
    List<Resource> getAllResources();
    Optional<Resource> getResourceById(int id);
    Resource createResource(Resource resource);
    Resource updateResource(int id, Resource resource);
    void deleteResource(int id);
}
