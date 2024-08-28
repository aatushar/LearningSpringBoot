package com.khajana.setting.repository.paymentmode;

import com.khajana.setting.entity.paymentmode.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentModeRepository extends JpaRepository<PaymentMode, Long> {
    Optional<PaymentMode> findPaymentModeById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deletePaymentModeById(Long id);
}
