package com.khajana.setting.dto.transactiontype;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportLcPurposeRequestDto {
    @NotBlank(message = "purpose Field Is Required")
    @Size(max = 250, message = "purpose Maximum Length is 250 character")
    private String purpose;
    @NotBlank(message = "for use Field Is Required")
    @Size(max = 100, message = "for use Maximum Length is 100 character")
    private String forUse;
    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "1.00", message = "Sequence Number must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "Sequence Number must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "Active can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;

}
