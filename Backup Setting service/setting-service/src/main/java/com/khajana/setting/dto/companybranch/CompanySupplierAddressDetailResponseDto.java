package com.khajana.setting.dto.companybranch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanySupplierAddressDetailResponseDto {
    private Long id;
    private Long supplierId;
    private Long addressTypeId;
    private String address;
    private String postalCode;
    private Long upazilaId;
    private Long districtId;
    private Long countryId;
    private Boolean isDefault;
    private Boolean active;
    private String supplierName;
    private String addressTypeName;
    private String upazillaName;
    private String districtName;
    private Long divisionId;
    private String divisionName;
    private String countryName;
}
