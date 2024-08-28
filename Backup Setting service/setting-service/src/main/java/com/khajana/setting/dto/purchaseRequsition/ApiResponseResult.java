package com.khajana.setting.dto.purchaseRequsition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
    public class ApiResponseResult<T> {
        private List<T> content;
        private long totalElements;
        private int totalPages;
        private int page;
        private int size;
        private List<String> sort;
}
