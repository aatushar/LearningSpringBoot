package com.khajana.setting.dto.purchaseRequsition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DemoMasterList {
    private Long id;
    private String requisitionNumber;
    private String requisitionDate;
    private Long companyId;
    private Long branchId;
    private Long storeId;
    private Long prodTypeId;
    private String prodTypeName;
    private String prodTypeNameBn;
    private Long masterGroupId;
    private String itmMstrGroupName;
    private String itmMstrGrpNameBn;
    private String submittedBy;
    private String submittedAt;
    private String submittedByName;
}
