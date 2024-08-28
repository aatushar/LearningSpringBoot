package com.khajana.setting.dto.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemGroupRequestDto {
    @NotNull(message = "requiered field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long itmMstrGrpId;
    @NotNull(message = "requiered field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String itmGrpPrefix;
    @NotNull(message = "requiered field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long uomSetId;
    @NotNull(message = "requiered field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String itmGrpName;
    @NotNull(message = "requiered field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String itmGrpNameBn;
    @Size(max = 50, message = "length can be maximum of 50 characters")
    @NotNull(message = "requiered field can not be null or empty")
    private String itemGrpDes;
    @NotNull(message = "requiered field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String itemGrpDesBn;
    @NotNull(message = "requiered field can not be null or empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
}