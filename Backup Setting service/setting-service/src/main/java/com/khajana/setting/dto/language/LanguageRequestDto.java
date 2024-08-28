package com.khajana.setting.dto.language;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.IntegerDeserializer;
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
public class LanguageRequestDto {

    @NotNull(message = "name can not be empty")
    @Size(max = 100, message = "name maximum lenght is 100 character")
    private String name;
    @NotNull(message = "code can not be empty")
    @Size(max = 100, message = "code maximum lenght is 100 character")
    private String code;
    @NotNull(message = "appLangCode can not be empty")
    @Size(max = 255, message = "appLangCode maximum lenght is 255 character")
    private String appLangCode;
    @NotNull(message = "rtl can not be empty")
    @JsonDeserialize(using = IntegerDeserializer.class)
    private Integer rtl;
    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "1.00", message = "Sequence Number must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "Sequence Number must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "active can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}