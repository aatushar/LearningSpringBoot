package com.khajana.setting.dto.companybranch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBranchResponseDto {

    private Long id;
    private Long companyId;
    private String companyName;
    private String companyNameBn;
    private String branchUnitName;
    private String branchUnitNameBn;
    private String branchUnitBinNumber;
    private String branchUnitBinNumberBn;
    private String branchUnitShortName;
    private String branchUnitShortNameBn;
    private String branchUnitVatRegistrationType;
    private String branchUnitCustomOfficeArea;
    private String branchUnitCustomOfficeAreaBn;
    private String branchUnitPhoneNumber;
    private String branchUnitEmailAddress;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

    private List<CompanyBranchAddressDetailResponseDto> addressDetails;
    private List<CompanyBranchBankDetailResponseDto> bankDetails;
    private List<CompanyBranchBusinessClassificationCodeResponseDto> classificationCodes;
    private List<CompanyBranchEconomicActivityResponseDto> economicActivities;
    private List<CompanyBranchEconomicActivityAreaResponseDto> economicActivityAreas;
}
