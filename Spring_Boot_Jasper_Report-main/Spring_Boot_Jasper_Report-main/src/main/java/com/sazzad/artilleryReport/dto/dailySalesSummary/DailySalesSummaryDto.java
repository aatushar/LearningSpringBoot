package com.sazzad.artilleryReport.dto.dailySalesSummary;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySalesSummaryDto {
    private String companyName;
    private String storeName;
    private String issueDate;
    private Double totalSaleAmount;
    private Double totalDiscountAmount;
    private Double totalPayableAmount;
    private Double totalInvoiceAmount;
    private Double totalPaidAmount;
    private Double dueAmount;

}
