package com.khajana.setting.repository.bank;

import com.khajana.setting.entity.bank.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankInfoRepository extends JpaRepository<BankInfo, Long> {
    Optional<BankInfo> findBankInfoById(Long id);

    boolean existsByBankName(String name);

    boolean existsByBankNameAndIdNot(String name, Long id);

    void deleteBankInfoById(Long id);
}
