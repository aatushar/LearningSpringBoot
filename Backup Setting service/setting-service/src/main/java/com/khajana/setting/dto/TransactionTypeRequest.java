package com.khajana.setting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTypeRequest {
    private int sourceTypeId;
    private String trnsTypeNameEn;
    private String trnsTypeNameBn;
    private int seqNo;
}
