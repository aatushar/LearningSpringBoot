package com.khajana.setting.dto.address;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoliceStationRequestDto {

    @NotNull(message = "districtId can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long districtId;
    @NotNull(message = "Name can not be empty")
    @Size(max = 50, message = "Name should not be greater than 50 characters")
    private String name;
    @NotNull(message = "Name BN can not be empty")
    @Size(max = 50, message = "Name BN should not be greater than 50 characters")
    private String nameBn;
    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "0.00", message = "sd must be at least 0.00")
    @DecimalMax(value = "9999999.99", message = "sd must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "active can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}