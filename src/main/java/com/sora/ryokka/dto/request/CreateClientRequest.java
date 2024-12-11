package com.sora.ryokka.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateClientRequest(@JsonProperty("firstName") String firstName,
                                  @JsonProperty("lastName") String lastName,

                                  @JsonProperty("contactEmail") String contactEmail,

                                  @JsonProperty("contactPhone") String contactPhone) {
}
