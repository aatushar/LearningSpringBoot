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
public class ItemMasterGroupRequestDto {
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long prodTypeId;
    @NotNull(message = "required field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String itmMstrGrpName;
    @NotNull(message = "required field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String itmMstrGrpNameBn;
    @NotNull(message = "required field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String itmMstrGrpPrefix;
    @NotNull(message = "required field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String itemMstrGrpDes;
    @NotNull(message = "required field can not be null or empty")
    @Size(max = 50, message = "length can be maximum of 50 characters")
    private String itemMstrGrpDesBn;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;
    @NotNull(message = "required field can not be null or empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
}