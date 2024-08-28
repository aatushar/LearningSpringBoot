package com.khajana.setting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseResult {
    private Object content;
    private int totalElements;
    private int totalPages;
    private int page;
    private int size;
    private List<String> sort;
}
