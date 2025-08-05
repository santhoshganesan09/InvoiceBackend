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

    public Invoice createInvoice(Invoice invoice) {
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
            return repo.save(updatedInvoice);
        });
    }

    public void deleteInvoice(Long id) {
        repo.deleteById(id);
    }
}
