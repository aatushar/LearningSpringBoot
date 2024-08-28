package com.khajana.setting.dto.companybranch;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBranchAddressDetailRequestDto {
    private Long branchId;
    private Long addressTypeId;
    private String holdingNo;
    private String roadNo;
    private Long upazilaId;
    private Long districtId;
    private Long policeStationId;
    private Long postalCode;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;


}
