package com.khajana.setting.dto.receive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveMasterResponseDto {
    private Long id;
    private Long issueMasterId;
    private Long creditNoteMasterId;
    private String txnCode;
    private Long importLcId;
    private Long udId;
    private Long tranSourceTypeId;
    private Long tranTypeId;
    private Long tranSubTypeId;
    private Long prodTypeId;
    private Long vatRebateId;
    private Long companyId;
    private Long branchId;
    private Long storeId;
    private Long currencyId;
    private Double excgRate;
    private Long supplierId;
    private Boolean isReg;
    private String supplierBinNumber;
    private String supplierBinNumberBn;
    private Long payModeId;
    private String payInstrumentNo;
    private String payInstrumentDate;
    private Long paymentInstitutionId;
    private Long bankBranchId;
    private Long bankAccountTypeId;
    private Boolean isRegBankTrans;
    private String supplierAccountNumber;
    private Date receiveDate;
    private Long fiscalYearId;
    private Long vatMonthId;
    private String grnNumber;
    private String grnNumberBn;
    private Date grnDate;
    private Long portOfDischargeId;
    private Short challanTypeId;
    private String challanNumber;
    private String challanNumberBn;
    private Date challanDate;
    private Double totalAssessableAmtTransCurr;
    private Double totalAssessableAmtLocalCurr;
    private Double recvAmtWotaxTransCurr;
    private Double recvAmtWotaxLocalCurr;
    private Double totalCdAmount;
    private Double totalRdAmount;
    private Double totalSdAmount;
    private Double totalVatAmount;
    private Double totalPaidVatAmount;
    private Double totalAtAmount;
    private Double totalAitAmount;
    private Double recvAmtWithtaxTransCurr;
    private Double recvAmtWithtaxLocalCurr;
    private Boolean monthlyProcStatus;
    private Boolean yearlyProcStatus;
    private Boolean isRebateable;
    private Boolean isVdsApplicable;
    private Boolean isVdsDone;
    private Boolean isPaid;
    private Boolean isBond;
    private String remarks;
    private String remarksBn;
    private Date createdAt;
    private Date updatedAt;
    private Long createdBy;
    private Long updatedBy;
}
