package com.example.InvoiceGenerator.controller;

import com.example.InvoiceGenerator.model.Invoice;
import com.example.InvoiceGenerator.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoice")
@CrossOrigin(origins = "*")
public class InvoiceController {

    @Autowired
    private InvoiceService service;

    @PostMapping
    public Invoice create(@RequestBody Invoice invoice) {
        return service.createInvoice(invoice);
    }

    @GetMapping
    public List<Invoice> getAll() {
        return service.getAllInvoices();
    }

    @GetMapping("/search")
    public List<Invoice> search(@RequestParam String keyword) {
        return service.searchInvoices(keyword);
    }

    @PutMapping("/{id}")
    public Optional<Invoice> update(@PathVariable Long id, @RequestBody Invoice invoice) {
        return service.updateInvoice(id, invoice);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteInvoice(id);
    }
}
