package com.sora.ryokka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sora.ryokka.model.ProjectImage;

import java.time.LocalDate;

public record ImageDataResponse (
        @JsonProperty("imageId") Long imageId,
        @JsonProperty("imageUrl") String imageUrl,
        @JsonProperty("fileName") String fileName,
        @JsonProperty("projectId") Long projectId,
        @JsonProperty("projectName") String projectName,
        @JsonProperty("isMainImage") Boolean isMainImage,
        @JsonProperty("projectDescription") String projectDescription,
        @JsonProperty("projectStatus") String projectStatus,
        @JsonProperty("projectLocation") String projectLocation,
        @JsonProperty("date") LocalDate date
) {
    public ImageDataResponse(ProjectImage projectImage) {
        this(
                projectImage.getId(),
                projectImage.getUrl(),
                projectImage.getFileName(),
                projectImage.getProject().getProjectId(),
                projectImage.getProject().getProjectName(),
                projectImage.getIsMainImage(),
                projectImage.getProject().getProjectDescription(),
                projectImage.getProject().getProjectStatus().getDescription(),
                projectImage.getProject().getProjectLocation(),
                projectImage.getDate()
        );
    }
}
