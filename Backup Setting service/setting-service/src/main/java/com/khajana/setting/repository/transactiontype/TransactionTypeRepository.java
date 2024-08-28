package com.khajana.setting.repository.transactiontype;

import com.khajana.setting.dto.transactiontype.TransactionTypeResponseDto;
import com.khajana.setting.entity.transactiontype.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
    void deleteSourceTypeById(Long id);

    void deleteTransactionTypeById(Long id);

    // Optional<TransactionType> findSourceTypeById(Long id);
    Optional<TransactionType> findTransactionTypeById(Long id);

    Optional<TransactionType> findTransactionTypeByTrnsTypeName(String trnsTypeName);

    @Query("SELECT new com.khajana.setting.dto.transactiontype.TransactionTypeResponseDto("
            + "t.id,t.sourceTypeId,j.tranSourceTypeName,j.tranSourceTypeNameBN,t.trnsTypeName,t.trnsTypeNameBn,t.seqNumber, "
            + "t.isActive,t.createdAt, t.createdBy) " + "FROM TransactionType t JOIN t.transactionSourceType j")
    public Page<TransactionTypeResponseDto> getJoinInformation(Pageable pageable);

    /*
     * @Query("SELECT new com.khajana.setting.dto.transactiontype.TransactionTypeResponseDto("
     * +
     * "t.id,t.sourceTypeId,j.tranSourceTypeName,j.tranSourceTypeNameBN,t.trnsTypeName,t.trnsTypeNameBn,t.seqNumber, "
     * + "t.isActive,t.createdAt, t.createdBy) " +
     * "FROM TransactionType t JOIN t.transactionSourceType j WHERE t.id = ?1")
     * public TransactionTypeResponseDto findTransactionTypeById(Long id);
     */
    boolean existsByTrnsTypeName(String trnsTypeName);

    boolean existsByTrnsTypeNameAndIdNot(String trnsTypeName, Long id);
}
