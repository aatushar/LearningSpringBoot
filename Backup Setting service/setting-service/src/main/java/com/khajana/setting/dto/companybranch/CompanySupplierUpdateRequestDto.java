package com.khajana.setting.dto.companybranch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanySupplierUpdateRequestDto {
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long companyId;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long vatRegId;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private long supplierTypeId;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 50, message = "maximum length 50 characters")
    private String supplierName;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 50, message = "maximum length 50 characters")
    private String supplierNameBn;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 13, message = "maximum length 13 characters")
    private String supplierBinNumber;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 13, message = "maximum length 13 characters")
    private String supplierBinNumberBn;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean registrationStatus;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
    @NotNull(message = "requied field can not be empty")
    @Email(message = "input should be type of email")
    private String emailAddress;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private long countryId;
}
