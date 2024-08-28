package com.khajana.setting.dto.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemStoreMappingRequestDto {
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long prodTypeId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long itmGrpSubId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long branchId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long storeId;
}