package com.khajana.setting.repository.bank;

import com.khajana.setting.entity.bank.BankAccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountTypeRepository extends JpaRepository<BankAccountType, Long> {
    Optional<BankAccountType> findBankAccountTypeById(Long id);

    boolean existsByBankAccountTypeName(String name);

    boolean existsByBankAccountTypeNameAndIdNot(String name, Long id);

    void deleteBankAccountTypeById(Long id);
}
