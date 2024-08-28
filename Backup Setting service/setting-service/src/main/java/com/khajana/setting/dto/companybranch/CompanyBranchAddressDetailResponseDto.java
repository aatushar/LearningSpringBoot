package com.khajana.setting.dto.companybranch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBranchAddressDetailResponseDto {
    private Long id;
    private Long branchId;
    private String branchName;
    private Long addressTypeId;
    private String addressTypeName;
    private Long postalCode;
    private Long districtId;
    private String districtName;
    private Long countryId;
    private String countryName;
    private Boolean active;
    private Long divisionId;
    private String divisionName;
    private Long upazillaId;
    private String upazillaName;
    private String address;


}
