package com.example.BillingSystem.service;

import com.example.BillingSystem.entity.Payment;
import com.example.BillingSystem.repository.PaymentRepository;
import com.example.BillingSystem.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Payment createPayment(Payment payment) {
        if (payment.getTransactionId() == null || payment.getTransactionId().isEmpty()) {
            payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase());
        }
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus("COMPLETED");
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByInvoice(Long invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId);
    }

    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }

    public List<Payment> getPaymentsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate);
    }

    public Payment updatePayment(Long id, Payment paymentDetails) {
        Payment payment = getPaymentById(id);
        if (paymentDetails.getStatus() != null) payment.setStatus(paymentDetails.getStatus());
        if (paymentDetails.getNotes() != null) payment.setNotes(paymentDetails.getNotes());
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public double getTotalRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Payment> payments = getPaymentsBetweenDates(startDate, endDate);
        return payments.stream()
                .filter(p -> "COMPLETED".equals(p.getStatus()))
                .mapToDouble(p -> p.getAmount().doubleValue())
                .sum();
    }
}
