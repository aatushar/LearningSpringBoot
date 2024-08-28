package com.khajana.setting.repository.customer;

import com.khajana.setting.entity.customer.CustomerContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerContactInfoRepository extends JpaRepository<CustomerContactInfo, Long> {
    Optional<CustomerContactInfo> findCustomerContactInfoById(Long id);

    void deleteCustomerContactInfoById(Long id);
}
