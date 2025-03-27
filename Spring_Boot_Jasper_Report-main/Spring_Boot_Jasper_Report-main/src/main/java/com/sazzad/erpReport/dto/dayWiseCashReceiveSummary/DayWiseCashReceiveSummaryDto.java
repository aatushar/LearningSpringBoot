package com.sazzad.erpReport.dto.dayWiseCashReceiveSummary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayWiseCashReceiveSummaryDto {

    private long sl;
    private long no;
    private String date;
    private double salesAmount;
    private double depositedAmount;
    private String remarks;
}
