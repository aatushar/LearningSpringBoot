package com.khajana.setting.dto.purchaseOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoGetAllDto {

    private Long id;
    private Long purchaseReqMasterId;
    public String purchaseOrderNumber;
    private Long companyId;
    private Long branchId;
    private Long storeId;
    private String purchaseOrderType;
    private Long masterGroupId;
    private String itmMstrGrpName;
    private String itmMstrGrpNameBn;
    private Date purchaseOrderDate;
    private Long supplierId;
    private String supplierName;
    private String supplierNameBn;
    private String deliveryPoint;
    private Date deliveryDate;
    private String payTem;
    private Double purchaseOrderAmount;
    private Boolean isActive;
    private Long collectorId;
    private String remarks;
    private String remarksBn;
    private String submittedBy;
    private Date submittedAt;
    private String recommendedBy;
    private  Date recommendedAt;
    private String approvedBy;
    private Date approvedAt;
    private Boolean approvedStatus;



}
