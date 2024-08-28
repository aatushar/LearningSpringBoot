package com.khajana.setting.dto.hscode;

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
public class HsCodeRequestDto {

    @NotNull(message = "hsCodeChapter field can not be null")
    @Size(max = 50, message = "hsCodeChapter should not be greater than 50 characters")
    private String hsCodeChapter;
    @NotNull(message = "hsCodeHeading field can not be null")
    @Size(max = 50, message = "hsCodeHeading should not be greater than 50 characters")
    private String hsCodeHeading;
    @NotNull(message = "hsCodeSection field can not be null")
    @Size(max = 50, message = "hsCodeSection should not be greater than 50 characters")
    private String hsCodeSection;
    @NotNull(message = "productCategoryId field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long productCategoryId;
    @NotNull(message = "hsCode field can not be null")
    @Size(max = 50, message = "hsCode should not be greater than 50 characters")
    private String hsCode;
    @NotNull(message = "description field can not be null")
    @Size(max = 100, message = "description should not be greater than 100 characters")
    private String description;
    @NotNull(message = "descriptionBn field can not be null")
    @Size(max = 100, message = "descriptionBn should not be greater than 100 characters")
    private String descriptionBn;
    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "1.00", message = "Sequence Number must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "Sequence Number must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "active field can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}