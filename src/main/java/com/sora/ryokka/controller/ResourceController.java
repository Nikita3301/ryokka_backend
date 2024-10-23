package com.sora.ryokka.controller;

import com.sora.ryokka.dto.request.CreateResourceRequest;
import com.sora.ryokka.dto.response.ResourceDataResponse;
import com.sora.ryokka.exception.ResourceNotFoundException;
import com.sora.ryokka.model.Resource;
import com.sora.ryokka.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<List<ResourceDataResponse>> getAllResources() {
        List<Resource> resources = resourceService.getAllResources();

        // Map Resource entities to ResourceDataResponse
        List<ResourceDataResponse> resourceResponses = resources.stream()
                .map(ResourceDataResponse::new)
                .collect(Collectors.toList());

        return resourceResponses.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(resourceResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceDataResponse> getResourceById(@PathVariable Long id) {
        Optional<Resource> resource = resourceService.getResourceById(id);
        return resource.map(r -> new ResponseEntity<>(new ResourceDataResponse(r), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ResourceDataResponse>> getResourcesByProjectId(@PathVariable Long projectId) {
        List<Resource> resources = resourceService.getResourcesByProjectId(projectId);


        List<ResourceDataResponse> resourceResponses = resources.stream()
                .map(ResourceDataResponse::new)
                .collect(Collectors.toList());

        return resourceResponses.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(resourceResponses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResourceDataResponse> createResource(@RequestBody CreateResourceRequest request) {
        Resource createdResource = resourceService.createResource(request);
        return new ResponseEntity<>(new ResourceDataResponse(createdResource), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceDataResponse> updateResource(@PathVariable Long id, @RequestBody CreateResourceRequest request) {
        try {
            Resource updatedResource = resourceService.updateResource(id, request);
            return new ResponseEntity<>(new ResourceDataResponse(updatedResource), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        try {
            resourceService.deleteResource(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
