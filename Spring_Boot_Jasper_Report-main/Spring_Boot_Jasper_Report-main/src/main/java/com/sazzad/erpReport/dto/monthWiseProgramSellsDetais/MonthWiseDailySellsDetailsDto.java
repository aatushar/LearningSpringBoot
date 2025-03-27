package com.sazzad.erpReport.dto.monthWiseProgramSellsDetais;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthWiseDailySellsDetailsDto {
    private long sl;
    private String programName;
    private String customerName;
    private String phone;
    private String programDate;
    private String time;
    private Integer guests;
    private Double totalAccount;
    private Double paidAmount;
    private Double dueAmount;
    private String status;
}
