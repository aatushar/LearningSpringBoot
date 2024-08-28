package com.khajana.setting.repository.transactiontype;

import com.khajana.setting.entity.transactiontype.TransactionSubType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionSubTypeRepository extends JpaRepository<TransactionSubType, Long> {

    Optional<TransactionSubType> findSourceTypeById(Long id);

    Optional<TransactionSubType> findSourceTypeByTrnsSubTypeName(String trnsSubTypeName);

    boolean existsByTrnsSubTypeName(String trnsSubTypeName);

    boolean existsByTrnsSubTypeNameAndIdNot(String trnsSubTypeName, Long id);
    // @Query("SELECT new
    // com.khajana.setting.dto.transactiontype.TransactionSubTypeResponseDto("
    // +
    // "t.id,t.trnsTypeId,j.trnsTypeName,j.trnsTypeNameBn,t.trnsSubTypeName,t.trnsSubTypeNameBn,t.seqNo,
    // "
    // + "t.isActive,t.createdAt, t.createdBy) "
    // + "FROM TransactionSubType t JOIN t.transactionType j")
    // public Page<TransactionSubTypeResponseDto> getJoinInformation(Pageable
    // pageable);

    /*
     * @Query("SELECT new com.khajana.setting.dto.transactiontype.TransactionSubTypeResponseDto("
     * +
     * "t.id,t.trnsTypeId,j.trnsTypeName,j.trnsTypeNameBn,t.trnsSubTypeName,t.trnsSubTypeNameBn,t.seqNo, "
     * + "t.isActive,t.createdAt, t.createdBy) " +
     * "FROM TransactionSubType t JOIN t.transactionType j WHERE t.id = ?1") public
     * TransactionSubTypeResponseDto findTransactionSubTypeById(Long id);
     */

}
