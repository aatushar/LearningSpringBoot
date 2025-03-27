package com.sazzad.erpReport.dto.isssueReturnSummary;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueReturnSummaryDto {

    private long sl;
    private long issueNo;
    private String issueDate;
    private String returningStore;
    private String receiveStore;
    private double issueAmount;
    private double returnAmount;
    private String remarks;
}
