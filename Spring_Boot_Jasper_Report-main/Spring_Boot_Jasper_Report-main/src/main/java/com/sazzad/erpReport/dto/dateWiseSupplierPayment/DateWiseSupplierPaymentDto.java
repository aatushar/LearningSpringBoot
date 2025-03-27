package com.sazzad.erpReport.dto.dateWiseSupplierPayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateWiseSupplierPaymentDto {
    private Long sl;
    private Long no;
    private String supplier;
    private Double grnAmount;
    private Double paidAmount;
    private String remarks;
}
