package com.khajana.setting.dto.supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {
    private Long id;
    //    private Long companyIdD;
//    private Long vatRegistrationIdD;
    private String vatRegType;
    private String supplierNameD;
    private String supplierNameBnD;
    private String supplierBinNoD;
    private String supplierBinNoBnD;
//    private boolean registrationStatus;
//    private boolean isActive;
//    private Date createdAt;
    // private int createdBy;
}
