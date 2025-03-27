package com.sazzad.artilleryReport.dto.totalDueListByCourse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalDueListByCourseDto {


    private String companyName;
    private String storeName;
    private String custName;
    private Double totalSaleAmount;
    private Double totalDiscountAmount;
    private Double totalPayableAmount;
    private Double totalInvoiceAmount;
    private Double totalPaidAmount;
    private Double dueAmount;
    private String remarks;
}
