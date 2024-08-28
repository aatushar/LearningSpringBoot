package com.khajana.setting.dto.companystore;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCompanyStoreMappingUpsertResponseDto {

    private Long userId;
    private String name;

    private List<StoreMappingDtoByUser> stores;


//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class StoreMapping {
//        private Long id;
//        private Long storeId;
//        private Boolean isDefault;
//        private String userName;
//        private String storeName;
//        private String storeNameBn;
//        private String branchName;
//        private String companyName;
//        private Boolean active;
//        private String address;
//        private String createdAt;
//        private String updatedAt;
//        private String createdBy;
//        private String updatedBy;
//    }
}
