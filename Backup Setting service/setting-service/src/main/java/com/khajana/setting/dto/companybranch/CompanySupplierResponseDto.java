package com.khajana.setting.dto.companybranch;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanySupplierResponseDto {
    private Long id;
    private Long companyId;
    private String companyName;
    private Long supplierTypeId;
    private String supplierTypeName;
    private Long vatRegId;
    private String vatRegTypeName;
    private String supplierName;
    private String supplierNameBn;
    private String supplierBinNumber;
    private String supplierBinNumberBn;
    private Boolean registrationStatus;
    private String email;
    private String emailVerifiedAt;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;


    private List<CompanySupplierAddressDetailResponseDto> addresses;


    private List<CompanySupplierBankDetailResponseDto> bankDetails;


    private CompanySupplierContactInfoResponseReturnDto contactInfos;
}
