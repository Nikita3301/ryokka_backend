// InvoiceDataResponse.java
package com.sora.ryokka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sora.ryokka.model.Invoice;
import com.sora.ryokka.model.InvoiceStatus;

import java.sql.Date;

public record InvoiceDataResponse(
        @JsonProperty("invoiceId") Long invoiceId,
        @JsonProperty("invoiceName") String invoiceName,
        @JsonProperty("invoiceDescription") String invoiceDescription,
        @JsonProperty("issueDate") Date issueDate,
        @JsonProperty("totalAmount") Double totalAmount,
        @JsonProperty("invoiceStatus") String invoiceStatus
) {
    public InvoiceDataResponse(Invoice invoice) {
        this(
                invoice.getInvoiceId(),
                invoice.getInvoiceName(),
                invoice.getInvoiceDescription(),
                invoice.getIssueDate(),
                invoice.getTotalAmount(),
                invoice.getInvoiceStatus().getDescription()
        );
    }
}
