package com.sazzad.erpReport.dto.dailySalesVsCashReceive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySalesVsCashReceiveDto {
    private long sl;
    private String salesPoint;
    private Double totalSales;
    private Double discount;
    private Double vat;
    private Double receivables;
    private Double paidByCustomer;
    private Double dueToCustomer;
    private Double bankPoint;
    private Double cardPmt;
    private Double mfs;
    private Double grandTotal;
    private Double cashReceived;
    private Double dueToSalesPoint;
    private String remarks;


}
