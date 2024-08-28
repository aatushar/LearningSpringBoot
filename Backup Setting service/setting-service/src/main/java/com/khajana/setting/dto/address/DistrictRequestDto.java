package com.khajana.setting.dto.address;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictRequestDto {
    @NotNull(message = "divisionId can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long divisionId;
    @Max(value = 30, message = "longitude Number should be less than or equal to 30")
    private String longitude;
    @Max(value = 30, message = "latitude should be less than or equal to 30")
    private String lat;
    @NotNull(message = "name can not be empty")
    @Size(max = 50, message = "name should not be greater than 50 characters")
    private String name;
    @NotNull(message = "nameBn can not be empty")
    @Size(max = 50, message = "name bn should not be greater than 50 characters")
    private String nameBn;
    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "1.00", message = "Sequence Number must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "Sequence Number must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "active can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}