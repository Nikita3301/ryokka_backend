package com.sora.ryokka.controller.images;

import com.sora.ryokka.dto.response.ImageDataResponse;
import com.sora.ryokka.model.ProjectImage;
import com.sora.ryokka.service.ProjectImagesService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/project-images")
public class ProjectImagesController {

    private final ProjectImagesService projectImagesService;

    public ProjectImagesController(ProjectImagesService projectImagesService) {
        this.projectImagesService = projectImagesService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ImageDataResponse>> getAllImages() {
        try {
            List<ImageDataResponse> images = projectImagesService.getAllImages();
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @PostMapping("/{projectId}/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,  @PathVariable Long projectId, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        String url = projectImagesService.uploadProjectImage(file, projectId, date);
        return ResponseEntity.ok(url);
    }

    @PostMapping("/{projectId}/uploadImages")
    public ResponseEntity<List<String>> uploadImages(
            @RequestParam("files") List<MultipartFile> files,
            @PathVariable Long projectId,
            @RequestParam("dates") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<LocalDate> dates) {

        List<String> urls = projectImagesService.uploadProjectImages(files,dates, projectId);
        return ResponseEntity.ok(urls);
    }


    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectImages(@PathVariable Long projectId) {
        try {
            List<ProjectImage> projectImages = projectImagesService.getProjectImages(projectId);


            List<ImageDataResponse> imageDataResponses = new ArrayList<>();

            for (ProjectImage projectImage : projectImages) {
                ImageDataResponse dto = new ImageDataResponse(projectImage);
                imageDataResponses.add(dto);
            }

            return ResponseEntity.ok(imageDataResponses);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching images.");
        }
    }

    @PostMapping("/{imageId}/main")
    public ResponseEntity<String> setMainImage(@PathVariable Long imageId, @RequestParam Long projectId) {
        try {
            projectImagesService.setMainImage(imageId, projectId);
            return ResponseEntity.ok("Image set as main successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to set image as main: " + e.getMessage());
        }
    }


    @GetMapping("/main/{projectId}")
    public ResponseEntity<?> getMainProjectImage(@PathVariable Long projectId) {
        try {

            ProjectImage mainImage = projectImagesService.getMainImage(projectId);
            ImageDataResponse response = new ImageDataResponse(mainImage);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching images.");
        }
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        return projectImagesService.deleteImageById(imageId);
    }
}