package com.khajana.setting.dto.companybranch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanySupplierUpdateResponseDto {
    private Long id;
    private Long companyId;
    private String companyName;
    private Long vatRegId;
    private String vatRegTypeName;
    private long supplierTypeId;
    private String supplierName;
    private String supplierNameBn;
    private String supplierBinNumber;
    private String supplierBinNumberBn;
    private Integer supplierPhoneNumber;
    private Boolean registrationStatus;
    private Boolean active;
    private String email;
    private String emailVerifiedAt;
    private long countryId;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
