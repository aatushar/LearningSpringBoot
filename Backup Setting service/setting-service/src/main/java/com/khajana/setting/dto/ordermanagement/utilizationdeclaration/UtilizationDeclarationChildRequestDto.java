package com.khajana.setting.dto.ordermanagement.utilizationdeclaration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilizationDeclarationChildRequestDto {
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long bblcId;
    @NotNull
    private String remarks;
}
