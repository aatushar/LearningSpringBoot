package com.khajana.setting.dto.companybranch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanySupplierBankDetailResponseDto {
    private Long id;
    private Long supplierId;
    private String supplierName;
    private String bankName;
    private String bankBranchName;
    private String bankAccountTypeName;
    private Long bankId;
    private Long bankBranchId;
    private Long bankAccountTypeId;
    private String supplierAccountNumber;
    private String supplierAccountNumberBn;
}
