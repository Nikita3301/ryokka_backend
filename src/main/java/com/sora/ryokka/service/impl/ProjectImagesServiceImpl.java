package com.sora.ryokka.service.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.sora.ryokka.dto.response.ImageDataResponse;
import com.sora.ryokka.exception.ResourceNotFoundException;
import com.sora.ryokka.model.Project;
import com.sora.ryokka.model.ProjectImage;
import com.sora.ryokka.repository.ImageRepository;
import com.sora.ryokka.repository.ProjectRepository;
import com.sora.ryokka.service.ProjectImagesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectImagesServiceImpl implements ProjectImagesService {

    private final ImageRepository imageRepository;

    private final ProjectRepository projectRepository;

    public ProjectImagesServiceImpl(ImageRepository imageRepository, ProjectRepository projectRepository) {
        this.imageRepository = imageRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public List<ImageDataResponse> getAllImages() {
        List<ProjectImage> projectImages = imageRepository.findAll();
        List<ImageDataResponse> imageDataResponses = new ArrayList<>();

        for (ProjectImage projectImage : projectImages) {
            imageDataResponses.add(new ImageDataResponse(projectImage));
        }

        return imageDataResponses;
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("ryokka-359c6.appspot.com", fileName); // Replace with your bucket name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
        InputStream inputStream = ProjectImagesServiceImpl.class.getClassLoader().getResourceAsStream("firebase/firebase_config.json");
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/ryokka-359c6.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public String uploadProjectImage(MultipartFile file, Long projectId, LocalDate date) {
        String imageUrl = null;
        File tempFile = null;
        try {
            String fileName = file.getOriginalFilename();
            if (fileName != null) {
                fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
                tempFile = new File(fileName);
                try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                    fos.write(file.getBytes());
                }
                imageUrl = this.uploadFile(tempFile, fileName);
            }

            if (tempFile != null) {
                deleteTempFile(tempFile);
            }

            Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

            ProjectImage projectImage = new ProjectImage();
            projectImage.setUrl(imageUrl);
            projectImage.setFileName(fileName);
            projectImage.setProject(project);
            projectImage.setDate(date);
            imageRepository.save(projectImage);

            return imageUrl;
        } catch (Exception e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }


    @Override
    public List<String> uploadProjectImages(List<MultipartFile> files, List<LocalDate> dates, Long projectId) {
        List<String> imageUrls = new ArrayList<>();

        try {
            Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                LocalDate date = dates.get(i);

                String fileName = file.getOriginalFilename();
                fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));
                File tempFile = this.convertToFile(file, fileName);
                try {
                    String imageUrl = this.uploadFile(tempFile, fileName);
                    ProjectImage projectImage = new ProjectImage();
                    projectImage.setUrl(imageUrl);
                    projectImage.setFileName(fileName);
                    projectImage.setProject(project);
                    projectImage.setDate(date);
                    imageRepository.save(projectImage);
                    imageUrls.add(imageUrl);
                } finally {
                    deleteTempFile(tempFile);
                }

            }

        } catch (Exception e) {
            throw new RuntimeException("Image upload failed", e);
        }

        return imageUrls;
    }


    @Override
    public void setMainImage(Long imageId, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

        ProjectImage currentMainImage = imageRepository.findByProjectAndIsMainImageTrue(project);
        if (currentMainImage != null) {
            currentMainImage.setIsMainImage(false);
            imageRepository.save(currentMainImage);
        }

        ProjectImage newMainImage = imageRepository.findById(imageId).orElseThrow(() -> new RuntimeException("Image not found"));
        newMainImage.setIsMainImage(true);
        imageRepository.save(newMainImage);
    }

    @Override
    public List<ProjectImage> getProjectImages(Long projectId) {
        return imageRepository.findByProjectProjectId(projectId);
    }

    @Override
    public ProjectImage getMainImage(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        return imageRepository.findByProjectAndIsMainImageTrue(project);
    }


    @Override
    public ResponseEntity<?> deleteImageById(Long imageId) {
        if (!imageRepository.existsById(imageId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found with id: " + imageId);
        }
        imageRepository.deleteById(imageId);
        return ResponseEntity.ok().body("Image deleted successfully.");
    }

    private void deleteTempFile(File tempFile) throws IOException {
        if (tempFile.exists()) {
            boolean deleted = Files.deleteIfExists(tempFile.toPath());
            if (!deleted) {
                System.err.println("Failed to delete temporary file: " + tempFile.getAbsolutePath());
            }
        }
    }


}