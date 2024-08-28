package com.khajana.setting.dto.issue;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WastageManagementChildRequestDto {
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long itemInfoId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long uomId;
    @NotNull(message = "required field can not be empty")
    private String uomShortCode;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double relativeFactor;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double issueQty;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double itemRate;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double issueRate;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = LongDeserializer.class)
    private Long vatRateTypeId;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean isFixedRate;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double cdPercent;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double cdAmount;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double rdPercent;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double rdAmount;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double sdPercent;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double sdAmount;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double vatPercent;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double vatAmount;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double aitPercent;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double aitAmount;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double atPercent;
    @NotNull(message = "required field can not be empty")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double atAmount;
}
