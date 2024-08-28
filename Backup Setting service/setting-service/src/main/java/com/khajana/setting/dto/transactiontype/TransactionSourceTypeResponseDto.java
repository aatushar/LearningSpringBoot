package com.khajana.setting.dto.transactiontype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSourceTypeResponseDto {
    private Long id;
    private String tranSourceTypeName;
    private String tranSourceTypeNameBN;
    private Double seqNo;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}