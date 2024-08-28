package com.khajana.setting.dto.companystore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyStoreResponseDto {
    private Long id;
    private Long branchId;
    private String companyBranchName;
    private String companyBranchNameBn;
    private String companyName;
    private String slCode;
    private String slName;
    private String slNameBn;
    private String slShortName;
    private String slShortNameBn;
    private String slAddress;
    private String slAddressBn;
    // private Long slOfficerId;
    private Long slType;
    private String slTypeName;
    private String slTypeNameBn;
    private Boolean isDefaultLocation;
    private Boolean isSalesPoint;
    private Boolean isVirtualLocation;
    private Long ticketCatId;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
