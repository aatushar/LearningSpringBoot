package com.khajana.setting.dto.companybranch;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBranchEconomicActivityAreaResponseDto {
    private Long id;
    private Long branchId;
    private Long economicActivityAreaId;
    private String otherDetail;
    private Double seqNo;
    private Boolean active;
    private String branchName;
    private String economicActivityAreaName;
    // private String createdAt;
    // private String updatedAt;
    // private String createdBy;
    // private String updatedBy;


}
