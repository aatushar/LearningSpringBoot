package com.khajana.setting.repository.vat;

import com.khajana.setting.dto.vat.VatPaymentMethodeResponseDto;
import com.khajana.setting.entity.vat.VatPaymentMethodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VatPaymentMethodeRepo extends JpaRepository<VatPaymentMethodeEntity, Long> {

    Optional<VatPaymentMethodeEntity> findVatPaymentTypeById(Long id);

    boolean existsByVatPaymentMethodName(String trnsTypeName);

    boolean existsByVatPaymentMethodNameAndIdNot(String trnsTypeName, Long id);

    @Query("SELECT new com.khajana.setting.dto.vat.VatPaymentMethodeResponseDto("
            + "t.id,t.tranSubTypeId,j.trnsSubTypeName,j.trnsSubTypeNameBn,t.vatPaymentMethodName,t.vatPaymentMethodNameBn,t.seqNo, "
            + "t.isActive,t.createdAt, t.createdBy) " + "FROM VatPaymentMethodeEntity t JOIN t.transactionSubType j")
    public Page<VatPaymentMethodeResponseDto> getJoinInformation(Pageable pageable);

    /*
     * @Query("SELECT new com.khajana.setting.dto.vat.VatPaymentMethodeResponseDto("
     * +
     * "t.id,t.tranSubTypeId,j.trnsSubTypeName,j.trnsSubTypeNameBn,t.vatPaymentMethodName,t.vatPaymentMethodNameBn,t.seqNo, "
     * + "t.isActive,t.createdAt, t.createdBy) " +
     * "FROM VatPaymentMethodeEntity t JOIN t.transactionSubType j WHERE t.id = ?1")
     * public VatPaymentMethodeResponseDto findVatPaymentMethodById(Long id);
     */
}
