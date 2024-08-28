package com.khajana.setting.dto.item;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSubGroupRequestDto {
    @NotNull(message = "requiered field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long itmGrpId;
    @NotNull(message = "requiered field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long hsCodeId;
    @NotNull(message = "requiered field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String itmSubGrpName;
    @NotNull(message = "requiered field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String itmSubGrpNameBn;
    @NotNull(message = "requiered field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String itmSubGrpPrefix;
    @NotNull(message = "requiered field can not be null or empty")
    private Long invMethodId;
    @NotNull(message = "requiered field can not be null or empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
    @NotNull(message = "requiered field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
}