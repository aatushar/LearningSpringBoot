package com.khajana.setting.dto.productRequisition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class IndentMasterDto {
    private Long id;
    private Long orderMasterId;
    private String indentNumber;
    private LocalDate indentDate;
    private Boolean isProductReq;
    private Long companyId;
    private Long branchId;
    private Long prodTypeId;
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
    private String purpose;
    private String remarks;
    private String remarksBn;
    private Long submittedBy;
    private LocalDateTime submittedAt;
    private Long recommendedBy;
    private LocalDateTime recommendedAt;
    private Long approvedBy;
    private LocalDateTime approvedAt;
    private Boolean approvedStatus;
    private Boolean isForIssue;
    private Boolean isIssued;
    private Boolean isForPurchase;
    private Boolean isIndentClosed;
    private Boolean isProReqClose;

}

