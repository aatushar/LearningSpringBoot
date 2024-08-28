package com.khajana.setting.repository.customer;

import com.khajana.setting.entity.customer.CustomerBankDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerBankDetailRepository extends JpaRepository<CustomerBankDetail, Long> {
    Optional<CustomerBankDetail> findCustomerBankDetailById(Long id);

    void deleteCustomerBankDetailById(Long id);
}
