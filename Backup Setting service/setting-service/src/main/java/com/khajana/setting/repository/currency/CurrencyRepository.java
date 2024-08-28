package com.khajana.setting.repository.currency;

import com.khajana.setting.entity.currency.Currency;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findCurrencyById(Long id);

    boolean existsByCurrencyShortCode(String name);

    boolean existsByCurrencyShortCodeAndIdNot(String name, Long id);

    Page<Currency> findAllCurrencyByCurrencyShortCode(String currencyShortCode, Pageable pageable);

    void deleteCurrencyById(Long id);
}
