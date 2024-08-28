package com.khajana.setting.dto.uom;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UomSetRequestDto {
    @NotNull(message = "uomSet  can not be empty")
    @Size(max = 12, message = "uomSet should not be greater than 12 characters")
    private String uomSet;
    @NotNull(message = "uomSetDesc  can not be empty")
    @Size(max = 50, message = "uomSetDesc should not be greater than 50 characters")
    private String uomSetDesc;
    @NotNull(message = "localUomSetDesc  can not be empty")
    @Size(max = 50, message = "localUomSetDesc should not be greater than 50 characters")
    private String localUomSetDesc;
    @NotNull(message = "Active can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}