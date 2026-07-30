package com.example.BillingSystem.repository;

import com.example.BillingSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByInvoiceId(Long invoiceId);
    List<Payment> findByStatus(String status);
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
