package com.khajana.setting.controller.purchaseRequsition;

import com.khajana.setting.dto.productRequisition.ApiResponse;
import com.khajana.setting.dto.purchaseRequsition.DemoInsertPkDto;
import com.khajana.setting.service.purchaseRequsiton.DemoInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/demomaster")
public class DemoInsertController {

    @Autowired
    private DemoInsertService demoInsertService;

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse> insertDemoData(@RequestBody DemoInsertPkDto demoInsertPkDto) {
        ApiResponse response = demoInsertService.insertData(demoInsertPkDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Other methods
}
