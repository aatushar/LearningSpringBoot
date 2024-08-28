package com.khajana.setting.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressDetailResponseDto {
    private Long id;
    private Long customerId;
    private Long addressTypeId;
    private String address;
    private String postalCode;
    private Long upazilaId;
    private Long districtId;
    private Long countryId;
    private Boolean isDefault;
    private Boolean active;
    private String customerName;
    private String addressTypeName;
    private String upazillaName;
    private String districtName;
    private Long divisionId;
    private String divisionName;
    private String countryName;
}
