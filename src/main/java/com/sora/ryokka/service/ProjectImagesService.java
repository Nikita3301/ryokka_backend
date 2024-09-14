package com.sora.ryokka.service;

import com.sora.ryokka.dto.response.ImageDataResponse;
import com.sora.ryokka.model.ProjectImage;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface ProjectImagesService {

    List<ImageDataResponse> getAllImages();

    String uploadProjectImage(MultipartFile file, Integer projectId, LocalDate date);

    void setMainImage(Integer imageId, Integer projectId);

    List<ProjectImage> getProjectImages(Integer projectId);

    ProjectImage getMainImage(Integer projectId);

    List<String> uploadProjectImages(List<MultipartFile> files, List<LocalDate> dates, Integer projectId);

    void deleteImageById(Integer imageId);
}