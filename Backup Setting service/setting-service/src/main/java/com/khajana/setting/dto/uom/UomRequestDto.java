package com.khajana.setting.dto.uom;

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
public class UomRequestDto {

    @JsonDeserialize(using = LongDeserializer.class)
    private Long uomSetId;
    @NotNull(message = "uomShortCode can not be empty")
    @Size(max = 12, message = "uomShortCode  should not be greater than 12 characters")
    private String uomShortCode;
    @NotNull(message = "uomDesc can not be empty")
    @Size(max = 50, message = "uomDesc  should not be greater than 50 characters")
    private String uomDesc;
    @NotNull(message = "uomLocalDesc can not be empty")
    @Size(max = 50, message = "uomLocalDesc  should not be greater than 50 characters")
    private String uomLocalDesc;
    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "1.00", message = "sd must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "sd must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double relativeFactor;
    @NotNull(message = "fractionAllow can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean fractionAllow;
    @NotNull(message = "Active can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;

}
