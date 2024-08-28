package com.khajana.setting.controller;

import com.khajana.setting.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/setting/api/v1/test")
public class TestController {

    @GetMapping("/")
    public ResponseEntity<ApiResponse> hwl() {
        return new ResponseEntity<ApiResponse>(new ApiResponse(200, "API is working", ""), HttpStatus.OK);
    }
}
