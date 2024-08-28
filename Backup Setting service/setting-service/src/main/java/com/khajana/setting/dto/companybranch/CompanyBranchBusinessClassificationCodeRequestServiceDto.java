package com.khajana.setting.dto.companybranch;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBranchBusinessClassificationCodeRequestServiceDto {
    @Nullable
    private Long id;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long hsCodeId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long commercialDescriptionOfSupply;
    @NotNull(message = "required field can not be empty")
    @Size(max = 100)
    private String descriptionOfHsCode;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double seqNo;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean active;


}
