package com.khajana.setting.dto.productRequisition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndentChildDto {

    @NotNull(message = "Item Info ID is required")
    private Long itemInfoId;

    @NotNull(message = "UOM ID is required")
    private Long uomId;

    @NotNull(message = "UOM Short Code is required")
    private String uomShortCode;

    @NotNull(message = "Relative Factor is required")
    private Double relativeFactor;

    // Nullable fields
    private BigDecimal indentQuantity;
    private BigDecimal consumOrderQty;
    private String remarks;
}
