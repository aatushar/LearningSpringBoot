package com.khajana.setting.dto.transactiontype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSubTypeResponseDto {

    private Long id;
    private Long trnsTypeId;
    private String trnsTypeName;
    private String trnsTypeNameBn;
    private String trnsSubTypeName;
    private String trnsSubTypeNameBn;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}