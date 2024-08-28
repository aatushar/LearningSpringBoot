package com.khajana.setting.dto.companystore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCompanyStoreMappingResponseDto {
    private Long id;
    private Long userId;
    private Long storeId;
    private String userName;
    private String storeName;
    private String storeNameBn;
    private String branchName;
    private String companyName;
    private Boolean isDefault;
    private Boolean active;
    private String address;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
