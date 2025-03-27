package com.sazzad.erpReport.dto.paymentoSupplierInclServiceTwo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentToSupplierInclServiceTwoDto {
    private long sl;
    private long no;
    private String date;
    private Double grnAmount;
    private Double paidAmount;
    private String remarks;
}
