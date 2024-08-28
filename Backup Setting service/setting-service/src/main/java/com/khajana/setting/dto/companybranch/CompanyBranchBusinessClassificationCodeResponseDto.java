package com.khajana.setting.dto.companybranch;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBranchBusinessClassificationCodeResponseDto {
    private Long id;
    private Long branchId;
    private Long hsCodeId;
    private Long commercialDescriptionOfSupply;
    private String descriptionOfHsCode;
    private Double seqNo;
    private Boolean active;
    private String branchName;
    // private String createdAt;
    // private String updatedAt;
    // private String createdBy;
    // private String updatedBy;


}
