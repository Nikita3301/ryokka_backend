package com.sora.ryokka.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.sql.Date;

public record CreateProjectRequest(
        @NotBlank
        @JsonProperty("projectName")
        String projectName,

        @NotBlank
        @JsonProperty("projectDescription")
        String projectDescription,

        @JsonProperty("projectLocation")
        String projectLocation,

        @NotNull
        @JsonProperty("projectStatus")
        String projectStatus,

        @NotNull
        @JsonProperty("startDate")
        Date startDate,

        @JsonProperty("endDate")
        Date endDate,

        @Positive
        @JsonProperty("projectBudget")
        Double projectBudget,

        @NotNull
        @JsonProperty("clientId")
        Long clientId
) {
}
