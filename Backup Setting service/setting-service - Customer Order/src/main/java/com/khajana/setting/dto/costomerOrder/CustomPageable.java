package com.khajana.setting.dto.costomerOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomPageable {
    private int pageNo;
    private int pageSize;
    private String dbField;
    private String sortDirection;
    private String filter;
}
