package com.khajana.setting.dto.companystore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreMappingDtoByUser {
    private Long id;
    private Long storeId;
    private Boolean isDefault;
    private String storeName;
    private String storeNameBn;
    private String branchName;
    private String companyName;
    private String address;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
