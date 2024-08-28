package com.khajana.setting.dto.product;

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
public class ProductTypeRequestDto {

    @NotNull(message = "product category Id can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long productCategoryId;
    @NotNull(message = "name can not be empty")
    @Size(max = 100, message = "name maximum lenght is 100 character")
    private String name;
    @NotNull(message = "nameBn can not be empty")
    @Size(max = 100, message = "name maximum lenght is 100 character")
    private String nameBn;
    @NotNull(message = "seqNo can not be empty")
    @DecimalMin(value = "1.00", message = "fixed rate must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "fixed rate must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "active can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}