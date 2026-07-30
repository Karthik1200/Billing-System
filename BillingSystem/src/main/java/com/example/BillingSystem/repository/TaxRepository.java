package com.example.BillingSystem.repository;

import com.example.BillingSystem.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {
    Optional<Tax> findByName(String name);
    Optional<Tax> findByTaxCode(String taxCode);
    List<Tax> findByActiveTrue();
}
