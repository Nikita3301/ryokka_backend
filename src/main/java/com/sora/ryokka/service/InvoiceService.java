package com.sora.ryokka.service;

import com.sora.ryokka.model.Invoice;
import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    List<Invoice> getAllInvoices();
    Optional<Invoice> getInvoiceById(int id);
    Invoice createInvoice(Invoice invoice);
    Invoice updateInvoice(int id, Invoice invoice);
    void deleteInvoice(int id);
}
