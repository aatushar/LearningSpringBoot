package com.khajana.setting.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateResponseDto {
    private Long id;
    private Long companyId;
    private String companyName;
    // private Long vatRegId;
    private long customerTypeId;
    private String customerName;
    private String customerNameBn;
    private String customerBinNumber;
    private String customerBinNumberBn;
    private Integer customerPhoneNumber;
    private Boolean registrationStatus;
    private Boolean active;
    private String emailAddress;
    private String emailVerifiedAt;
    private long countryId;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
