package com.khajana.setting.repository.transactiontype;

import com.khajana.setting.entity.transactiontype.TransactionSourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionSourceTypeRepository extends JpaRepository<TransactionSourceType, Long> {
    void deleteSourceTypeById(Long id);

    boolean existsByTranSourceTypeName(String tranSourceTypeName);

    boolean existsByTranSourceTypeNameAndIdNot(String tranSourceTypeName, Long id);

    Optional<TransactionSourceType> findSourceTypeById(Long id);

    Optional<TransactionSourceType> findSourceTypeByTranSourceTypeName(String tranSourceTypeName);

}
