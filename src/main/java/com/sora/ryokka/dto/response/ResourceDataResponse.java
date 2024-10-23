package com.sora.ryokka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sora.ryokka.model.Resource;

public record ResourceDataResponse(
        @JsonProperty("resourceId") Long resourceId,
        @JsonProperty("resourceName") String resourceName,
        @JsonProperty("resourceDescription") String resourceDescription,
        @JsonProperty("resourceType") String resourceType,
        @JsonProperty("unitOfMeasure") String unitOfMeasure,
        @JsonProperty("unitCost") Double unitCost,
        @JsonProperty("quantity") Double quantity,
        @JsonProperty("generalPrice") Double generalPrice
) {
    public ResourceDataResponse(Resource resource) {
        this(
                resource.getResourceId(),
                resource.getResourceName(),
                resource.getResourceDescription(),
                resource.getResourceType(),
                resource.getUnitOfMeasure(),
                resource.getUnitCost(),
                resource.getQuantity(),
                resource.getGeneralPrice()
        );
    }
}
