package com.sora.ryokka.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateResourceRequest {

    @NotBlank(message = "Resource name is required")
    private String resourceName;

    private String resourceDescription;

    private String resourceType;

    @NotNull(message = "Unit cost is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit cost must be greater than zero")
    private Double unitCost;

    private String unitOfMeasure;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Quantity must be greater than zero")
    private Double quantity;

    @NotNull(message = "General price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "General price must be greater than zero")
    private Double generalPrice;

    @NotNull(message = "Project ID is required")
    private Long projectId;
}
