package com.sazzad.erpReport.dto.issueSummary;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueSummaryDto {
  private Integer sl;
  private Integer no;
  private String issueDate;
  private String issueStore;
  private String receiveStore;
  private Double amount;
  private String remarks;

}
