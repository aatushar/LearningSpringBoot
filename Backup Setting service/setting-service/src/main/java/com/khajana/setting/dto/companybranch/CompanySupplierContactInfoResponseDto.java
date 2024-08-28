package com.khajana.setting.dto.companybranch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanySupplierContactInfoResponseDto {
    private Long id;
    private String contactPerson;
    private String phone;
}
