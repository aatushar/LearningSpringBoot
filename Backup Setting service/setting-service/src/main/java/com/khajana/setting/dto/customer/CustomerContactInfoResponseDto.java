package com.khajana.setting.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerContactInfoResponseDto {
    private Long id;
    private Long customerId;
    private String customerCode;
    private String contactPersonDisplayCode;
    private String contactPersonShortName;
    private String contactPerson;
    private Long contactPersonPhone;
    private String contactPersonTinNum;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
