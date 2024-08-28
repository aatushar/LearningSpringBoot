package com.khajana.setting.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBankDetailResponseDto {
    private Long id;
    private Long customerId;
    private Long bankId;
    private Long bankBranchId;
    private Long bankAccountTypeId;
    private String customerAccountNumber;
    private String customerAccountNumberBn;
    private Boolean active;
    // private String createdAt;
    // private String updatedAt;
    // private String createdBy;
    // private String updatedBy;
    private String customerName;
    private String bankName;
    private String bankBranchName;
    private String bankAccountTypeName;
}
