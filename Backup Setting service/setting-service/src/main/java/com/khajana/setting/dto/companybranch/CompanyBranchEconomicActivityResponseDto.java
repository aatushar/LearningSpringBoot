package com.khajana.setting.dto.companybranch;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBranchEconomicActivityResponseDto {
    private Long id;
    private Long branchId;
    private Long economicActivityId;
    private String supportingDocNo;
    private String supportingDocIssueDate;
    private Double seqNo;
    private Boolean active;
    private String branchName;
    private String economicActivityName;
    // private String createdAt;
    // private String updatedAt;
    // private String createdBy;
    // private String updatedBy;
}
