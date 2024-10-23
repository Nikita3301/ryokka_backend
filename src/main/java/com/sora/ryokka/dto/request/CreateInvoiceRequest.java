package com.sora.ryokka.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sora.ryokka.model.InvoiceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class  CreateInvoiceRequest{

        @NotNull
        @JsonProperty("projectId")
        private Long projectId;

        @NotBlank
        @JsonProperty("invoiceName")
        private String invoiceName;

        @NotBlank
        @JsonProperty("invoiceDescription")
        private String invoiceDescription;

        @NotNull
        @JsonProperty("issueDate")
        private Date issueDate;

        @NotNull
        @JsonProperty("totalAmount")
        private Double totalAmount;

        @NotNull
        @JsonProperty("invoiceStatus")
        private InvoiceStatus invoiceStatus;

}
