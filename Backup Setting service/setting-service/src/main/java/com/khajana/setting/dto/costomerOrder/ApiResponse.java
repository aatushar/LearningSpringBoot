package com.khajana.setting.dto.costomerOrder;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private Integer code;
    private String message;
    private List<CustomKeyValue> error;
    private Object result;
}