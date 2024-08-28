package com.khajana.setting.dto.companybranch;


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
public class CompanyBranchAddressDetailRequestServiceDto {
    @Nullable
    private Long id;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long addressTypeId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long postalCode;
    @NotNull(message = "required field can not be empty")
    @Size(max = 50, message = "maximum of 50 characters")
    private String address;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long upazillaId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long divisionId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long districtId;


}
