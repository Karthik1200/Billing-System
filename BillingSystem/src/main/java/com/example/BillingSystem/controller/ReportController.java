package com.example.BillingSystem.controller;

import com.example.BillingSystem.entity.Invoice;
import com.example.BillingSystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardMetrics() {
        Map<String, Object> metrics = reportService.getDashboardMetrics();
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/sales")
    public ResponseEntity<Map<String, Object>> getSalesReport(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {
        if (startDate == null) startDate = LocalDateTime.now().minusMonths(1);
        if (endDate == null) endDate = LocalDateTime.now();
        Map<String, Object> report = reportService.getSalesReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/payments")
    public ResponseEntity<Map<String, Object>> getPaymentReport(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {
        if (startDate == null) startDate = LocalDateTime.now().minusMonths(1);
        if (endDate == null) endDate = LocalDateTime.now();
        Map<String, Object> report = reportService.getPaymentReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/overdue-invoices")
    public ResponseEntity<List<Invoice>> getOverdueInvoices() {
        List<Invoice> invoices = reportService.getOverdueInvoices();
        return ResponseEntity.ok(invoices);
    }
}
