package com.example.BillingSystem.service;

import com.example.BillingSystem.entity.Invoice;
import com.example.BillingSystem.entity.Payment;
import com.example.BillingSystem.repository.InvoiceRepository;
import com.example.BillingSystem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public Map<String, Object> getDashboardMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        List<Invoice> allInvoices = invoiceRepository.findAll();
        List<Payment> allPayments = paymentRepository.findAll();

        BigDecimal totalRevenue = allPayments.stream()
                .filter(p -> "COMPLETED".equals(p.getStatus()))
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOutstanding = allInvoices.stream()
                .filter(i -> "PENDING".equals(i.getStatus()))
                .map(Invoice::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        metrics.put("totalRevenue", totalRevenue);
        metrics.put("totalOutstanding", totalOutstanding);
        metrics.put("totalInvoices", allInvoices.size());
        metrics.put("totalPayments", allPayments.size());

        return metrics;
    }

    public Map<String, Object> getSalesReport(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Object> report = new HashMap<>();

        List<Invoice> invoices = invoiceRepository.findByInvoiceDateBetween(startDate, endDate);
        BigDecimal totalSales = invoices.stream()
                .map(Invoice::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        report.put("period", "From " + startDate + " to " + endDate);
        report.put("totalInvoices", invoices.size());
        report.put("totalSales", totalSales);

        return report;
    }

    public Map<String, Object> getPaymentReport(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Object> report = new HashMap<>();

        List<Payment> payments = paymentRepository.findByPaymentDateBetween(startDate, endDate);
        BigDecimal totalPayments = payments.stream()
                .filter(p -> "COMPLETED".equals(p.getStatus()))
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        report.put("period", "From " + startDate + " to " + endDate);
        report.put("totalPayments", payments.size());
        report.put("totalAmount", totalPayments);

        return report;
    }

    public List<Invoice> getOverdueInvoices() {
        LocalDateTime now = LocalDateTime.now();
        return invoiceRepository.findAll().stream()
                .filter(i -> i.getDueDate() != null && i.getDueDate().isBefore(now) && !("PAID".equals(i.getStatus())))
                .toList();
    }
}
