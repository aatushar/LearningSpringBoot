package com.khajana.setting.dto.policestation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
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
public class PoliceStationsRequestDto {
    @NotNull(message = "districtId can not be empty")
    private Long districtId;
    @NotNull(message = "psName can not be empty")
    @Size(max = 50, message = "Name should not be greater than 50 characters")
    private String psName;
    @NotNull(message = "psNameBn can not be empty")
    @Size(max = 50, message = "NameBn should not be greater than 50 characters")
    private String psNameBn;
    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "1", message = "Sequence Number must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "Sequence Number must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "Active can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}
