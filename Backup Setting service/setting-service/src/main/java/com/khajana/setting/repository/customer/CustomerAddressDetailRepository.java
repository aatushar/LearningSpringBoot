package com.khajana.setting.repository.customer;

import com.khajana.setting.entity.customer.CustomerAddressDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerAddressDetailRepository extends JpaRepository<CustomerAddressDetail, Long> {
    Optional<CustomerAddressDetail> findCustomerAddressDetailById(Long id);

    // void deleteCustomerAddressDetailByById(Long id);
}
