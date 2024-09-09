package com.sora.ryokka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sora.ryokka.model.Client;

public record ClientDataResponse(
        @JsonProperty("clientId")
        int clientId,
        @JsonProperty("firstName")
        String firstName,
        @JsonProperty("lastName")
        String lastName,
        @JsonProperty("contactPhone")
        String contactPhone,
        @JsonProperty("contactEmail")
        String contactEmail
) {
    public ClientDataResponse(Client client) {
        this(
                client.getClientId(),
                client.getFirstName(),
                client.getLastName(),
                client.getContactPhone(),
                client.getContactEmail()
        );
    }
}
