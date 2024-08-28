package com.khajana.setting.repository.issue;

import com.khajana.setting.entity.issue.IssueMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DamageManagementRepository extends JpaRepository<IssueMaster, Long> {
    Optional<IssueMaster> findIssueMasterById(Long id);

    Page<IssueMaster> findAllByTranTypeIdAndProdTypeId(Long transactionTypeId, Long productTypeId, Pageable pageable);

    void deleteIssueMasterById(Long id);
}
