package com.khajana.setting.repository.customer;

import com.khajana.setting.entity.customer.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerById(Long id);

    Optional<Customer> findIssuedCustomerById(Long id);

    Page<Customer> findAllCustomerByCustomerNameContaining(String customerName, Pageable pageable);

    void deleteCustomerById(Long id);
}
