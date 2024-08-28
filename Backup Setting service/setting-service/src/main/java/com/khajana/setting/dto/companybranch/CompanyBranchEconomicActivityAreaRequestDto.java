package com.khajana.setting.dto.companybranch;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBranchEconomicActivityAreaRequestDto {
    private Long branchId;
    private Long economicActivityAreaId;
    private String otherDetail;
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;


}
