package com.khajana.setting.dto.customer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressDetailRequestDto {
    @Nullable
    @JsonDeserialize(using = LongDeserializer.class)
    private Long id;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long addressTypeId;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 50)
    private String address;
    @NotNull(message = "requied field can not be empty")
    @Size(max = 50)
    private String postalCode;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long upazilaId;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long divisionId;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long districtId;
    @NotNull(message = "requied field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long countryId;
}
