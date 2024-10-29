package com.sora.ryokka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sora.ryokka.model.Client;

import java.util.List;
import java.util.stream.Collectors;

public record ClientDataResponse(
        @JsonProperty("clientId")
        Long clientId,
        @JsonProperty("firstName")
        String firstName,
        @JsonProperty("lastName")
        String lastName,
        @JsonProperty("contactPhone")
        String contactPhone,
        @JsonProperty("contactEmail")
        String contactEmail,
        @JsonProperty("projects")
        List<ProjectDataResponse> projects
) {
    public ClientDataResponse(Client client) {
        this(
                client.getClientId(),
                client.getFirstName(),
                client.getLastName(),
                client.getContactPhone(),
                client.getContactEmail(),
                client.getProjects() == null ? List.of() : client.getProjects().stream()
                        .map(ProjectDataResponse::new)
                        .collect(Collectors.toList())
        );
    }
}
