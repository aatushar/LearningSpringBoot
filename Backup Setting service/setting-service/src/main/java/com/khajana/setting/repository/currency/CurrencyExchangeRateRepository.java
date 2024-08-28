package com.khajana.setting.repository.currency;

import com.khajana.setting.entity.currency.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Long> {
    Optional<CurrencyExchangeRate> findCurrencyExchangeRateById(Long id);

    boolean existsByExchangeRate(String exchangeRate);

    boolean existsByExchangeRateAndIdNot(String exchangeRate, Long id);

    void deleteCurrencyExchangeRateById(Long id);
}
