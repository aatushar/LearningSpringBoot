package com.khajana.setting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private int code;
    private String message;
    // private List<CustomKeyValue> error;
    private Object result;
}
