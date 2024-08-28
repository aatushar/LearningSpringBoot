package com.khajana.setting.dto.vdspurchaser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VdsPurchaserResponseDto {

    private Long id;
    private Long branchId;
    private String branchName;
    private String branchNameBn;
    private String transactionDate;
    private Long vmId;
    private String vmName;
    private String certificateNo;
    private String publishedDate;
    private Long supplierId;
    private String supplierName;
    private Long tcMasterId;
    private Double totalRecvAmtWotaxLocalCurr;
    private Double totalVatAmount;
    private Double totalDeductedVatAmount;
    private Double totalVdsPaidAmount;
    private Boolean isPaid;
    private Boolean active;
    private String createdAt;
    private String createdBy;

}