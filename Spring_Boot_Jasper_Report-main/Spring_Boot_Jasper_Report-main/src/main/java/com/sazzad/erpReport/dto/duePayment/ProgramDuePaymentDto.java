package com.sazzad.erpReport.dto.duePayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProgramDuePaymentDto {
    private int sl;
    private String programName;
    private String customerName;
    private String phone;
    private String programDate;
    private String time;
    private int guests;
    private double totalAmount;
    private double paidAmount;
    private double dueAmount;


}
