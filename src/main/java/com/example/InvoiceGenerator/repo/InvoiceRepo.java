package com.example.InvoiceGenerator.repo;

import com.example.InvoiceGenerator.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface InvoiceRepo extends JpaRepository<Invoice,Long> {

    List<Invoice> findByPhoneContainingOrEmailContainingOrCustomerNameContainingOrInvoiceNoContaining(
            String Phone,
            String email,
            String customerName,
            String invoiceNo);

    Collection<? extends Invoice> findByPaymentMethod(Invoice.PaymentMethod method);
}
