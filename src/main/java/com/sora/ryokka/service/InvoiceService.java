package com.sora.ryokka.service;

import com.sora.ryokka.dto.request.CreateInvoiceRequest;
import com.sora.ryokka.model.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    List<Invoice> getAllInvoices();

    Optional<Invoice> getInvoiceById(Long id);

    List<Invoice> getInvoicesByProjectId(Long projectId);

    Invoice createInvoice(CreateInvoiceRequest request);

    Invoice updateInvoice(Long id, CreateInvoiceRequest request);

    void deleteInvoice(Long id);

}
