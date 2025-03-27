package com.sazzad.erpReport.dto.indentSummary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndentSummaryDto {
    private Integer  sl;
    private String indentNo;
    private String date;
    private String indentingStore;
    private String programName;
    private String programDate;
    private String issueStatus;
    private String closeStatus;
    private String remarks;

}
