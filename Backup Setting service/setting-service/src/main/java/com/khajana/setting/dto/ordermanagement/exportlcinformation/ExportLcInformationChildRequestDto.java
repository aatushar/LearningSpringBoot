package com.khajana.setting.dto.ordermanagement.exportlcinformation;

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
public class ExportLcInformationChildRequestDto {
    // @NotNull
    // @JsonDeserialize(using = LongDeserializer.class)
    // private Long lcId;
    @NotNull
    @JsonDeserialize(using = LongDeserializer.class)
    private Long itemInfoId;
    @NotNull
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double qty;
    @NotNull
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double rate;
    @NotNull
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double amount;
}
