package com.khajana.setting.dto.issue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WastageManagementResponseDto {
    private Long id;
    private Long receiveMasterId;
    private Long prodTypeId;
    private String prodTypeName;
    private String prodTypeNameBn;
    private String compName;
    private String compNameBn;
    private Long companyId;
    private Long branchId;
    private String branchUnitName;
    private String branchUnitNameBn;
    private Long storeId;
    // private String slName;
    // private String slNameBn;
    private Long currencyId;
    private String currencyShortCode;
    private String currencyDesc;
    private Double excgRate;
    // private Long customerId;
    private Long fiscalYearId;
    private String fiscalYear;
    private String fiscalYearBn;
    private Long vatMontId;

    private String vmInfo;
    private String vmInfoBn;
    private String issueNo;
    private String issueNoBn;
    private String issueDate;
    private Long departmentId;

    private String departmentName;
    private String departmentNameBn;
    private Double totalIsAmBeDiscount;
    private Double totalIsAmLocalCurrBeDiscount;
    private Double totalCdAmount;
    private Double totalRdAmount;
    private Double totalSdAmount;
    private Double totalVatAmount;
    private Double totalIssueAmtTransCurr;
    private Double totalIssueAmtLocalCurr;
    private String remarks;
    private String remarksBn;
    private String createdAt;
    private String createdBy;

    private Long tranSourceTypeId;
    private Long tranTypeId;
    private Long tranSubTypeId;
    private String tranSoureName;
    private String tranTypeName;
    private String tranSubTypeName;
}
