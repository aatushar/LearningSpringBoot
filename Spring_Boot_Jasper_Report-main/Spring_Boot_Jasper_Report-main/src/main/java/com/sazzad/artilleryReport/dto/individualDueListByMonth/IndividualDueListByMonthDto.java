package com.sazzad.artilleryReport.dto.individualDueListByMonth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualDueListByMonthDto {

    private String companyName;
    private String storeName;
    private String monthName;
    private Double salesAmount;
    private Double discount;
    private Double payableAmount;
    private Double paidAmount;
    private Double dueAmount;
    private String remarks;
}
