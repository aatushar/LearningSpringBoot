package com.khajana.setting.dto.purchaseRequsition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoInsertPkDto {

    private String requisitionNumber;
    private Long companyId;
    private Long branchId;
    private Long storeId;
    private Long prodTypeId;
    private Long masterGroupId;
    private Date requisitionDate;
    private Long submittedBy;
    private Long recommendedBy;
    private Long approvedBy;
    private Boolean isPurReqClosed;
    private String remarks;
    private String remarksBn;
    private Long createdBy;
    private List<DemoInsertChildDto> prodReq;
    private List<DemoInsertChildDetailsDto> child;
}
