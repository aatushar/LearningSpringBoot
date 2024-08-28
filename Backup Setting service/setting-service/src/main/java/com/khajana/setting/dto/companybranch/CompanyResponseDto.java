package com.khajana.setting.dto.companybranch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDto {
    private Long id;
    private Long countryId;
    private String countryName;
    private Long currencyId;
    private String currencyName;
    private String compCode;
    private Long compTypeId;
    // private String compBusinessId;
    // private String compBusinessOthers;
    private String compName;
    private String compNameBn;
    private String compType;
    private String regPersonName;
    private String regPersonNameBn;
    private String regPersonNid;
    private String regPersonNidBn;
    private String compLogo;
    // private String compIcon;
    // private String natureOfBiz;
    private String compShortName;
    private String compShortNameBn;
    private String compAddress;
    private String compAddressBn;
    private String areaCode;
    private String areaCodeBn;
    private String phoneNumber;
    private String emailAddress;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
    private String companyTypeName;
}
