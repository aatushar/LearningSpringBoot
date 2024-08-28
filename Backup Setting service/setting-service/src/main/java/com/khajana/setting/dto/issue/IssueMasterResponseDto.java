package com.khajana.setting.dto.issue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueMasterResponseDto {
    private Long id;
    private Long indentMasterId;
    private Long receiveMasterId;
    private Long exportLcId;
    private Long tranSourceTypeId;
    private Long tranTypeId;
    private Long tranSubId;
    private Long prodTypeId;
    private Long companyId;
    private Long branchId;
    private Long storeId;
    private Long currencyId;
    private Double excgRate;
    private Long customerId;
    private Long emiMasterId;
    private Long regStatus;
    private String customerBinNo;
    private String customerBinNoBn;
    private Long payModeId;
    private String payInstrumentNo;
    private String payInstrumentDate;
    private Long bankBranchId;
    private Long bankAccountTypeId;
    private String customerAcctNumber;
    private Long isRegBankTrans;
    private String deliveryTo;
    private String deliveryToBn;
    private String deliveryPurpose;
    private Date deliveryDate;
    private Long fiscalYearId;
    private Long vatMontId;
    private Long customerOfficeId;
    private String issueDeliveryNo;
    private String issueDeliveryNoBn;
    private Date issueDate;
    private Long employeeId;
    private Long departmentId;
    private String requisitionNum;
    private String requisitionNumBn;
    private Date salesInvoiceDate;
    private Long isVdsApplicable;
    private Long isVdsDone;
    private Long portOfDischargeId;
    private Long challanTypeId;
    private String challanNumber;
    private String challanNumberBn;
    private Date cahallanDate;
    private String vehicleNum;
    private String vehicleNumBn;
    private Long vehicleTypeId;
    private Double totalIsAmBeDiscount;
    private Double totalIsAmLocalCurrBeDiscount;
    private Double totalDiscount;
    private Double totalCdAmount;
    private Double totalRdAmount;
    private Double totalSdAmount;
    private Double totalVatAmount;
    private Double totalIssueAmtTransCurr;
    private Double totalIssueAmtLocalCurr;
    private String remarks;
    private String remarksBn;
    private Long monthlyProcStatus;
    private Long yearlyProcStatus;
    private Long printStatus;
    private String createdAt;
    private String createdBy;
}
