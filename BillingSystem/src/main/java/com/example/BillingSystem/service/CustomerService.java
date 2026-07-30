package com.example.BillingSystem.service;

import com.example.BillingSystem.entity.Customer;
import com.example.BillingSystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getActiveCustomers() {
        return customerRepository.findByActiveTrue();
    }

    public List<Customer> getCustomersByCity(String city) {
        return customerRepository.findByCity(city);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        if (customerDetails.getName() != null) customer.setName(customerDetails.getName());
        if (customerDetails.getEmail() != null) customer.setEmail(customerDetails.getEmail());
        if (customerDetails.getPhone() != null) customer.setPhone(customerDetails.getPhone());
        if (customerDetails.getAddress() != null) customer.setAddress(customerDetails.getAddress());
        if (customerDetails.getCity() != null) customer.setCity(customerDetails.getCity());
        if (customerDetails.getState() != null) customer.setState(customerDetails.getState());
        if (customerDetails.getZipCode() != null) customer.setZipCode(customerDetails.getZipCode());
        if (customerDetails.getCountry() != null) customer.setCountry(customerDetails.getCountry());
        if (customerDetails.getTaxId() != null) customer.setTaxId(customerDetails.getTaxId());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customer.setActive(false);
        customerRepository.save(customer);
    }
}
