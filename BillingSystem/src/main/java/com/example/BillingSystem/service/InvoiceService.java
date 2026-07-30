package com.example.BillingSystem.service;

import com.example.BillingSystem.entity.*;
import com.example.BillingSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Invoice createInvoice(Invoice invoice) {
        if (invoice.getInvoiceNumber() == null || invoice.getInvoiceNumber().isEmpty()) {
            invoice.setInvoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setStatus("DRAFT");
        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> getInvoicesByCustomer(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }

    public List<Invoice> getInvoicesByStatus(String status) {
        return invoiceRepository.findByStatus(status);
    }

    public Invoice addItemToInvoice(Long invoiceId, InvoiceItem item) {
        Invoice invoice = getInvoiceById(invoiceId);
        item.setInvoice(invoice);
        invoiceItemRepository.save(item);
        updateInvoiceTotal(invoiceId);
        return invoice;
    }

    public void updateInvoiceTotal(Long invoiceId) {
        Invoice invoice = getInvoiceById(invoiceId);
        List<InvoiceItem> items = invoiceItemRepository.findByInvoiceId(invoiceId);

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;

        for (InvoiceItem item : items) {
            if (item.getTotalPrice() != null) {
                subtotal = subtotal.add(item.getTotalPrice());
            }
            if (item.getTaxAmount() != null) {
                totalTax = totalTax.add(item.getTaxAmount());
            }
        }

        invoice.setSubtotal(subtotal);
        invoice.setTaxAmount(totalTax);
        invoice.setTotalAmount(subtotal.add(totalTax).subtract(
                invoice.getDiscountAmount() != null ? invoice.getDiscountAmount() : BigDecimal.ZERO
        ));

        invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        Invoice invoice = getInvoiceById(id);
        if (invoiceDetails.getStatus() != null) invoice.setStatus(invoiceDetails.getStatus());
        if (invoiceDetails.getDueDate() != null) invoice.setDueDate(invoiceDetails.getDueDate());
        if (invoiceDetails.getNotes() != null) invoice.setNotes(invoiceDetails.getNotes());
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    public List<InvoiceItem> getInvoiceItems(Long invoiceId) {
        return invoiceItemRepository.findByInvoiceId(invoiceId);
    }
}
