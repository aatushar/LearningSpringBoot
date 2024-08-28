package com.khajana.setting.dto.companybranch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanySupplierContactInfoResponseReturnDto {
    private Long id;
    private Long supplierId;
    private String contactPerson;
    private String phone;
}
