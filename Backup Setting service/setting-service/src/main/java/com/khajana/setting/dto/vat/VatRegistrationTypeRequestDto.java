package com.khajana.setting.dto.vat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import io.micrometer.common.lang.Nullable;
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
public class VatRegistrationTypeRequestDto {
    @NotNull(message = "vatRegistrationName can not be empty")
    @Size(max = 50, message = "Name should not be greater than 50 characters")
    private String vatRegistrationName;

    @NotNull(message = "vatRegistrationNameBn BN can not be empty")
    @Size(max = 50, message = "Name BN should not be greater than 50 characters")
    private String vatRegistrationNameBn;
    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "1.00", message = "Sequence Number must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "Sequence Number must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "Active can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;

    @Nullable
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean reqBin;

}
