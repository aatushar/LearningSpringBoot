package com.khajana.setting.dto.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemUpdateRequestDto {
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long vatPaymentMethodId;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "maximum length will be 50 characters")
    private String itmCode;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "maximum length will be 50 characters")
    private String displayItmName;
    @NotNull(message = "required field can not be null")
    @Size(max = 50, message = "maximum length will be 50 characters")
    private String displayItmNameBn;
    @NotNull(message = "required field can not be null")

    @JsonDeserialize(using = LongDeserializer.class)
    private Long countryOrigin;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long hsCodeId;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long currencyInfoId;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long uomId;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long trnsUnitId;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long stockUnitId;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long salesUnitId;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean isRebateable;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean isTradeItem;
    @NotNull(message = "required field can not be null")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;

    @NotNull(message = "required field can not be null")
    private @Valid ItemStoreMappingUpdateRequestDto mapper;
}