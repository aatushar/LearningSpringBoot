package com.khajana.setting.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerContactInfoResponseReturnDto {
    private Long id;
    private Long customerId;
    private String contactPerson;
    private String contactPersonPhone;
}
