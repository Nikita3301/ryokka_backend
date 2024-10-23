package com.sora.ryokka.controller;

import com.sora.ryokka.dto.request.CreateInvoiceRequest;
import com.sora.ryokka.dto.response.InvoiceDataResponse;
import com.sora.ryokka.exception.ResourceNotFoundException;
import com.sora.ryokka.model.Invoice;
import com.sora.ryokka.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoices")
@Validated // Enable validation for request bodies
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        return invoice.map(i -> new ResponseEntity<>(i, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<InvoiceDataResponse>> getInvoicesByProjectId(@PathVariable Long projectId) {
        List<Invoice> invoices = invoiceService.getInvoicesByProjectId(projectId);

        // Map Invoice entities to InvoiceDataResponse
        List<InvoiceDataResponse> invoiceResponses = invoices.stream()
                .map(InvoiceDataResponse::new)
                .collect(Collectors.toList());

        return invoiceResponses.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(invoiceResponses, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody CreateInvoiceRequest request) {
        Invoice createdInvoice = invoiceService.createInvoice(request);
        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @Valid @RequestBody CreateInvoiceRequest request) {
        try {
            Invoice updatedInvoice = invoiceService.updateInvoice(id, request);
            return new ResponseEntity<>(updatedInvoice, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        try {
            invoiceService.deleteInvoice(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
