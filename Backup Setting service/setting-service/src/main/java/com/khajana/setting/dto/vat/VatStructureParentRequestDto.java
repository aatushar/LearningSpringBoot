package com.khajana.setting.dto.vat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatStructureParentRequestDto {
    @NotNull(message = "vatStructures field can not be empty")
    List<@Valid VatStructureRequestDto> vatStructures;
    @NotNull(message = "hsCodeId can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long hsCodeId;
    @NotNull(message = "fiscalYearId can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long fiscalYearId;

}
