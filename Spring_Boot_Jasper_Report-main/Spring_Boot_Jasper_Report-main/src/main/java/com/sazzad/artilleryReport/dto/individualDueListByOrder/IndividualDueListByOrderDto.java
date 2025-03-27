package com.sazzad.artilleryReport.dto.individualDueListByOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualDueListByOrderDto {
    private String companyName;
    private String storeName;
    private String orderNo;
    private String orderDate;
    private Double salesAmount;
    private Double discount;
    private Double payableAmount;
    private Double paidAmount;
    private Double dueAmount;
    private String remarks;

}
