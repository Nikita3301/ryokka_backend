package com.sora.ryokka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sora.ryokka.model.ProjectImage;
import com.sora.ryokka.model.Project;

import java.sql.Date;

public record ProjectDataResponse(
        @JsonProperty("projectId") Long projectId,
        @JsonProperty("projectName") String projectName,
        @JsonProperty("startDate") Date startDate,
        @JsonProperty("endDate") Date endDate,
        @JsonProperty("projectStatus") String projectStatus,
        @JsonProperty("projectLocation") String projectLocation,
        @JsonProperty("projectDescription") String projectDescription,
        @JsonProperty("projectBudget") Double projectBudget,
        @JsonProperty("mainImageUrl") String mainImageUrl
) {
    public ProjectDataResponse(Project project) {
        this(
                project.getProjectId(),
                project.getProjectName(),
                project.getStartDate(),
                project.getEndDate(),
                project.getProjectStatus().getDescription(),
                project.getProjectLocation(),
                project.getProjectDescription(),
                project.getProjectBudget(),
                getMainImageUrl(project)
        );
    }

    private static String getMainImageUrl(Project project) {
        for (ProjectImage projectImage : project.getProjectImages()) {
            if (projectImage.getIsMainImage()) {
                return projectImage.getUrl();
            }
        }
        return null;
    }
}
