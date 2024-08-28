package com.khajana.setting.dto.costomerOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostomerOrderIndexDto {

    private Date orderDate;
    private String customerName;
    private String phone;
    private String typeName;
    private String orderPkgType;
    private BigDecimal totalAmount;
    private String paymodeName;
    private Boolean SPODone;
    private String receiveStatus;
    private String paymentStatus;
    private String orderStatus;


}
