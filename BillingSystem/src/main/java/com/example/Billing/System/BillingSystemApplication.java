package com.example.Billing.System;

/**
 * Compatibility wrapper that delegates to the real main class in com.example.BillingSystem
 */
public class BillingSystemApplication {
    public static void main(String[] args) {
        com.example.BillingSystem.BillingSystemApplication.main(args);
    }
}
