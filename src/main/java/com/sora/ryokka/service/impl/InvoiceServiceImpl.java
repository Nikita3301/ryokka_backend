package com.sora.ryokka.service.impl;

import com.sora.ryokka.dto.request.CreateInvoiceRequest;
import com.sora.ryokka.exception.ResourceNotFoundException;
import com.sora.ryokka.model.Invoice;
import com.sora.ryokka.model.Project;
import com.sora.ryokka.repository.InvoiceRepository;
import com.sora.ryokka.repository.ProjectRepository;
import com.sora.ryokka.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProjectRepository projectRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ProjectRepository projectRepository) {
        this.invoiceRepository = invoiceRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public List<Invoice> getInvoicesByProjectId(Long projectId) {
        return invoiceRepository.findByProjectProjectId(projectId);
    }

    @Override
    public Invoice createInvoice(CreateInvoiceRequest request) {
        // Find the project by projectId
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + request.getProjectId()));

        // Create a new Invoice
        Invoice invoice = new Invoice();
        invoice.setInvoiceName(request.getInvoiceName());
        invoice.setInvoiceDescription(request.getInvoiceDescription());
        invoice.setIssueDate(request.getIssueDate());
        invoice.setTotalAmount(request.getTotalAmount());
        invoice.setInvoiceStatus(request.getInvoiceStatus());

        // Set the project
        invoice.setProject(project);

        // Save the invoice
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(Long id, CreateInvoiceRequest request) throws ResourceNotFoundException {
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));

        existingInvoice.setInvoiceName(request.getInvoiceName());
        existingInvoice.setInvoiceDescription(request.getInvoiceDescription());
        existingInvoice.setIssueDate(request.getIssueDate());
        existingInvoice.setTotalAmount(request.getTotalAmount());
        existingInvoice.setInvoiceStatus(request.getInvoiceStatus());

        return invoiceRepository.save(existingInvoice);
    }

    @Override
    public void deleteInvoice(Long id) throws ResourceNotFoundException {
        if (!invoiceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Invoice not found with id: " + id);
        }
        invoiceRepository.deleteById(id);
    }
}
