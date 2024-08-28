package com.khajana.setting.dto.productRequisition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBCommonInsertAck {
    private Integer status;
    private String message;
    private String result;

}

