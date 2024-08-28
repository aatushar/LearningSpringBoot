package com.khajana.setting.dto.ordermanagement.importlcinformation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportLcInformationChildRequestDto {

    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long itemInfoId;

    @NotNull
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double rate;

    @NotNull
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double importLcAmt;
}
