package com.khajana.setting.controller.purchaseOrder;


import com.khajana.setting.dto.purchaseOrder.ApiResponse;
import com.khajana.setting.service.purchaseOrder.PoGetOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/purcsase-order")
public class PoGetOneController {

    @Autowired
    private PoGetOneService poGetOneService;

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getPoGetOneById(@PathVariable("id") Long id) {
        ApiResponse response = poGetOneService.findPoGetOneById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
