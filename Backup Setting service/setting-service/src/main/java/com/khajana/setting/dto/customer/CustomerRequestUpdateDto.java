package com.khajana.setting.dto.customer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.EmailDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestUpdateDto {
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long companyId;
    @Nullable
    @JsonDeserialize(using = LongDeserializer.class)
    private Long vatRegId;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long customerTypeId;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 50, message = "maximum length 50 characters")
    private String customerName;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 50, message = "maximum length 50 characters")
    private String customerNameBn;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 13, message = "maximum length 13 characters")
    private String customerBinNumber;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 13, message = "maximum length 13 characters")
    private String customerBinNumberBn;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean registrationStatus;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = EmailDeserializer.class)
    private String emailAddress;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long countryId;

    @NotNull(message = "requied field can not be empty")
    private List<@Valid CustomerAddressDetailRequestDto> addresses;
    @NotNull(message = "requied field can not be empty")
    private List<@Valid CustomerBankDetailRequestDto> bankDetails;
    @NotNull(message = "requied field can not be empty")
    private @Valid CustomerContactInfoUpdateRequestDto contactInfos;
}
