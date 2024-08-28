package com.khajana.setting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBCommonInsertAck {
    private int currentId;
    private String message;
}
