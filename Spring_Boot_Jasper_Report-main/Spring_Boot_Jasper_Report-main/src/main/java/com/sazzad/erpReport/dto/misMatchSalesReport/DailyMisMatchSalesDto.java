package com.sazzad.erpReport.dto.misMatchSalesReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyMisMatchSalesDto {
    private Long id;
    private String storeName;
    private String transactionDate;
    private Double totalDiscount;
    private Double totalVat;
    private Double totalAmount;

}
