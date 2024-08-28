package com.khajana.setting.dto.issue;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.khajana.setting.deserializer.BooleanDeserializer;
import com.khajana.setting.deserializer.DoubleDeserializer;
import com.khajana.setting.deserializer.LongDeserializer;

// import jakarta.validation.constraints.DecimalMax;
// import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DamageManagementChildRequestDto {
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
    @NotNull(message = "Issue quantity is required and can not be empty")
    @Digits(integer = 13, fraction = 6, message = "Issue quantity must have up to 13 digits in total, with up to 6 decimal places")
    private Double issueQty;

    @NotNull(message = "Item rate is required and can not be empty")
    @Digits(integer = 11, fraction = 3, message = "Item rate must have up to 11 digits in total, with up to 3 decimal places")
    private Double itemRate;

    @NotNull(message = "Issue rate is required and can not be empty")
    @Digits(integer = 11, fraction = 3, message = "Issue rate must have up to 11 digits in total, with up to 3 decimal places")
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
