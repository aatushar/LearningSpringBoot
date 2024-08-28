package com.khajana.setting.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.DoubleDeserializer;
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
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
}
