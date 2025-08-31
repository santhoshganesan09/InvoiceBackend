package com.example.InvoiceGenerator.service;


import com.example.InvoiceGenerator.model.Invoice;
import com.example.InvoiceGenerator.repo.InvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepo repo;

    private static final Logger logger = Logger.getLogger(InvoiceService.class.getName());


    // 🔹 Generate sequential invoice number (INV-1001, INV-1002, ...)
    private String generateInvoiceNo() {
        long count = repo.count(); // number of invoices in DB
        return "INV-" + (1001 + count);
    }




    public Invoice createInvoice(Invoice invoice) {

        // only set invoiceNo if not already present (frontend doesn’t send it)
        if (invoice.getInvoiceNo() == null || invoice.getInvoiceNo().isEmpty()) {
            invoice.setInvoiceNo(generateInvoiceNo());
        }
        return repo.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return repo.findAll();
    }

    public List<Invoice> searchInvoices(String keyword) {
        List<Invoice> result = repo.findByPhoneContainingOrEmailContainingOrCustomerNameContainingOrInvoiceNoContaining(
                keyword, keyword, keyword, keyword);
        try {

            logger.info("Trying to parse keyword as PaymentMethod: " + keyword);
            Invoice.PaymentMethod method = Invoice.PaymentMethod.valueOf(keyword.toUpperCase());
            result.addAll(repo.findByPaymentMethod(method));
        } catch (IllegalArgumentException ignored) {
            logger.warning("Invalid PaymentMethod: " + keyword);
        }
        return result;
    }

    public Optional<Invoice> updateInvoice(Long id, Invoice updatedInvoice) {
        return repo.findById(id).map(existing -> {
            updatedInvoice.setId(existing.getId());
            // 👇 don’t change invoiceNo during update
            updatedInvoice.setInvoiceNo(existing.getInvoiceNo());
            return repo.save(updatedInvoice);
        });
    }

    public void deleteInvoice(Long id) {
        repo.deleteById(id);
    }
}
