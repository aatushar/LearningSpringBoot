package com.khajana.setting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse {
    private int code;
    private String message;
    private Result result;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        private List<Map<String, Object>> content;
        private int totalElements;
        private int totalPages;
        private int page;
        private int size;
        private List<String> sort;

        // Constructors, getters, and setters
        // ...
    }

}
