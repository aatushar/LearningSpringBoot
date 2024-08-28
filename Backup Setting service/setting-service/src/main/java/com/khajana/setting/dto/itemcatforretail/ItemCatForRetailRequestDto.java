package com.khajana.setting.dto.itemcatforretail;

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
public class ItemCatForRetailRequestDto {
    @NotNull(message = "itemCatRetailName can not be empty")
    @Size(max = 100, message = "itemCatRetailName should not be greater than 100 characters")
    private String itemCatRetailName;
    @NotNull(message = "itemCatRetailNameBn can not be empty")
    @Size(max = 100, message = "itemCatRetailNameBn should not be greater than 100 characters")
    private String itemCatRetailNameBn;
    @NotNull(message = "Sequence Number can not be empty")
    @DecimalMin(value = "1.00", message = "Sequence Number must be at least 1.00")
    @DecimalMax(value = "9999999.99", message = "Sequence Number must be at most 9999999.99")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "Active can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}
