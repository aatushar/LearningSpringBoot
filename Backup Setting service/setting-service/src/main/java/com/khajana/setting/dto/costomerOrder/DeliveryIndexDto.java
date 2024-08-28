package com.khajana.setting.dto.costomerOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryIndexDto {

    private Date orderDate;
    private String customerName;
    private String phone;
    private String typeName;
    private String orderPkgType;  // Assuming this is a constant text like 'Regular Order'
    private BigDecimal totalAmount; // Use BigDecimal for monetary values
    private String paymodeName;  // Assuming this is a text field
    private Boolean SPODone;     // Assuming this is a boolean field
    private String receiveStatus; // Assuming this is a text field
    private String paymentStatus; // Assuming this is a text field
    private String orderStatus;

}
