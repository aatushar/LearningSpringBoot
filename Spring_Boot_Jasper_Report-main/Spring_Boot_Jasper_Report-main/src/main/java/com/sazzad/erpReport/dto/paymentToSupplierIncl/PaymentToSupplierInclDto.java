package com.sazzad.erpReport.dto.paymentToSupplierIncl;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentToSupplierInclDto {

    private long sl;
    private long no;
    private String date;
    private long grnNumber;
    private String grnDate;
    private long grnAmount;
    private long paidAmount;
    private String remarks;
}
