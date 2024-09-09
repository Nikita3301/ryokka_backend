package com.sora.ryokka.service.impl;

import com.sora.ryokka.model.Invoice;
import com.sora.ryokka.repository.InvoiceRepository;
import com.sora.ryokka.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> getInvoiceById(int invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(int invoiceId, Invoice updatedInvoice) {
        return invoiceRepository.findById(invoiceId)
                .map(invoice -> {
                    invoice.setInvoiceName(updatedInvoice.getInvoiceName());
                    invoice.setInvoiceDescription(updatedInvoice.getInvoiceDescription());
                    invoice.setIssueDate(updatedInvoice.getIssueDate());
                    invoice.setTotalAmount(updatedInvoice.getTotalAmount());
                    invoice.setInvoiceStatus(updatedInvoice.getInvoiceStatus());
                    return invoiceRepository.save(invoice);
                })
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    @Override
    public void deleteInvoice(int invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }
}
