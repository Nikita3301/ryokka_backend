package com.sora.ryokka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sora.ryokka.model.Employee;

public record EmployeeDataResponse(
        @JsonProperty("employeeId") Long employeeId,
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("jobTitle") String jobTitle,
        @JsonProperty("phoneNumber") String phoneNumber,
        @JsonProperty("email") String email,
        @JsonProperty("imageUrl") String imageUrl
) {
    public EmployeeDataResponse(Employee employee) {
        this(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle(),
                employee.getPhoneNumber(),
                employee.getEmail(),
                employee.getImageUrl()
        );
    }
}
