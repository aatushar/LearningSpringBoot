package com.khajana.setting.controller.purchaseRequsition;


import com.khajana.setting.dto.productRequisition.ApiResponse;
import com.khajana.setting.service.purchaseRequsiton.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/indent")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getDemoByID(@PathVariable("id") Long id) {
        ApiResponse response = demoService.findDemoByID(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
