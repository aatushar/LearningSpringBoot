package com.khajana.setting.dto.purchaseRequsition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class DemoDto {

    private Long id;
    private String requisitonNumber;
    private String requisitionDate;
    private Long companyId;
    private Long branchId;
    private Long storeId;
    private Long prodTypeId;
    private String prodTypeName;
    private String prodTypeNameBn;
    private String prodCatId;
    private String prodCatName;
    private String prodCatNameBn;
    private Long masterGroupId;
    private String itmMstrGrpName;
    private String itmMstrGrpNameBn;
    private String submittedBy;
    private String submittedAt;
    private String submittedByName;
    private String recommendedBy;
    private String recommendedByName;
    private String recommendedAt;
    private String approvedBy;
    private String approvedByName;
    private String approvedAt;
    private Boolean approvedStatus;
    private Boolean isPartial;
    private Boolean isPurReqClosed;
    private Boolean isActive;
    private String remarks;
    private String remarksBn;
    private List<DemoChildDto> childs;
}
