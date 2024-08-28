package com.khajana.setting.dto.companybranch;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanySupplierRequestDto {
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long companyId;

    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long supplierTypeId;

    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long vatRegId;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String supplierName;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String supplierNameBn;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String supplierBinNumber;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String supplierBinNumberBn;

    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean registrationStatus;

    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    @Email(message = "input should be type of email")
    private String email;

    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;

    @Valid
    @NotNull
    private List<@Valid CompanySupplierAddressDetailRequestDto> addresses;

    @Valid
    @NotNull
    private List<@Valid CompanySupplierBankDetailRequestDto> bankDetails;

    @Valid
    @NotNull
    private CompanySupplierContactInfoRequestDto contactInfos;

}
