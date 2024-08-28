package com.khajana.setting.dto.companybranch;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBranchBankDetailResponseDto {
    private Long id;
    private Long branchId;
    private Long bankBranchId;
    private Long bankAccountTypeId;
    private String companyAccountNumber;
    private String companyAccountNumberBn;
    private Boolean active;
    private String bankName;
    private Long bankId;
    private String branchName;
    private String bankBanchName;
    private String bankAccountTypeName;
    // private String createdAt;
    // private String updatedAt;
    // private String createdBy;
    // private String updatedBy;


}
