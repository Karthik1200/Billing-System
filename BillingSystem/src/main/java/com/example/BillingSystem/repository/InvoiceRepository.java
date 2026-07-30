package com.example.BillingSystem.repository;

import com.example.BillingSystem.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    List<Invoice> findByCustomerId(Long customerId);
    List<Invoice> findByStatus(String status);
    List<Invoice> findByInvoiceDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
