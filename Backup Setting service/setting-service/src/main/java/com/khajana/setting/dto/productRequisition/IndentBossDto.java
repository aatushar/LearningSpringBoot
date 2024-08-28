package com.khajana.setting.dto.productRequisition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndentBossDto {
    private Long id;
    private Long orderMasterId;
    private String indentNumber;
    private LocalDate indentDate;
    private Boolean isProductReq;
    private Long companyId;
    private Long branchId;
    private Long prodTypeId;
    private String prodTypeName;
    private String prodTypeNameBn;
    private Long prodCatId;
    private String prodCatName;
    private String prodCatNameBn;
    private Long masterGroupId;
    private String itmMstrGrpName;
    private String itmMstrGrpNameBn;
    private Long demandStoreId;
    private String demandSlName;
    private String demandSlNameBn;
    private Long toStoreId;
    private String toStoreName;
    private String toStoreNameBn;
    private Long deptId;
    private String departmentName;
    private String departmentNameBn;
    private String purpose;
    private String remarks;
    private String remarksBn;
    private Long submittedBy;
    private Date submittedAt;
    private Long recommendedBy;
    private Date recommendedAt;
    private Long approvedBy;
    private Date approvedAt;
    private Boolean approvedStatus;
    private Boolean isForIssue;
    private Boolean isIssued;
    private Boolean isForPurchase;
    private Boolean isIndentClosed;
    private Boolean isProReqClose;
    private List<IndentChildBossDto> childs;

    // Getters and setters
}

