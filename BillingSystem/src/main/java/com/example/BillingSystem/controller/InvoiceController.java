package com.example.BillingSystem.controller;

import com.example.BillingSystem.entity.Invoice;
import com.example.BillingSystem.entity.InvoiceItem;
import com.example.BillingSystem.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice created = invoiceService.createInvoice(invoice);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Invoice>> getInvoicesByCustomer(@PathVariable Long customerId) {
        List<Invoice> invoices = invoiceService.getInvoicesByCustomer(customerId);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Invoice>> getInvoicesByStatus(@PathVariable String status) {
        List<Invoice> invoices = invoiceService.getInvoicesByStatus(status);
        return ResponseEntity.ok(invoices);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Invoice> addItemToInvoice(@PathVariable Long id, @RequestBody InvoiceItem item) {
        Invoice invoice = invoiceService.addItemToInvoice(id, item);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<InvoiceItem>> getInvoiceItems(@PathVariable Long id) {
        List<InvoiceItem> items = invoiceService.getInvoiceItems(id);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoiceDetails) {
        Invoice updated = invoiceService.updateInvoice(id, invoiceDetails);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok().build();
    }
}
