package com.sora.ryokka.service;

import com.sora.ryokka.dto.response.ImageDataResponse;
import com.sora.ryokka.model.ProjectImage;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface ProjectImagesService {

    List<ImageDataResponse> getAllImages();

    String uploadProjectImage(MultipartFile file, Long projectId, LocalDate date);

    void setMainImage(Long imageId, Long projectId);

    List<ProjectImage> getProjectImages(Long projectId);

    ProjectImage getMainImage(Long projectId);

    List<String> uploadProjectImages(List<MultipartFile> files, List<LocalDate> dates, Long projectId);

    void deleteImageById(Long imageId);
}