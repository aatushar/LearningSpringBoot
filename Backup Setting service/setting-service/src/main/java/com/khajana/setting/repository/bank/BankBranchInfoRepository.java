package com.khajana.setting.repository.bank;

import com.khajana.setting.entity.bank.BankBranchInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankBranchInfoRepository extends JpaRepository<BankBranchInfo, Long> {
    Optional<BankBranchInfo> findBankBranchInfoById(Long id);

    boolean existsByBankBranchName(String bankBranch);

    boolean existsByBankBranchNameAndIdNot(String bankBranch, Long id);

    void deleteBankBranchInfoById(Long id);
}
