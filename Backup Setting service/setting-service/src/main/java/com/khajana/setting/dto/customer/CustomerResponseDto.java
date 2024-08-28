package com.khajana.setting.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {
    private Long id;
    private Long companyId;
    // private Long vatRegId;
    private long customerTypeId;
    private String customerTypeName;
    private String companyName;
    private String customerName;
    private String customerNameBn;
    private String customerBinNumber;
    private String customerBinNumberBn;
    private Boolean registrationStatus;
    private String vatRegTypeName;
    private Boolean active;
    private String emailAddress;
    private String emailVerifiedAt;
    private long countryId;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
    private String contactPersonName;

    private List<CustomerAddressDetailResponseDto> addresses;
    private List<CustomerBankDetailResponseDto> bankDetails;
    private CustomerContactInfoResponseReturnDto contactInfos;
}
