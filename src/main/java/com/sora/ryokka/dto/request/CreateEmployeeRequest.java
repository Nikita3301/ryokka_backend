package com.sora.ryokka.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateEmployeeRequest(
        @NotBlank
        @JsonProperty("firstName")
        String firstName,

        @NotBlank
        @JsonProperty("lastName")
        String lastName,

        @NotBlank
        @JsonProperty("jobTitle")
        String jobTitle,

        @NotBlank
        @JsonProperty("phoneNumber")
        String phoneNumber,

        @NotBlank
        @Email
        @JsonProperty("email")
        String email
) {
}