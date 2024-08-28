package com.khajana.setting.controller.purchaseOrder;


import com.khajana.setting.dto.purchaseOrder.ApiResponse;
import com.khajana.setting.dto.purchaseOrder.PoInsertDto;
import com.khajana.setting.service.purchaseOrder.PoInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/purchaseOrder")
public class PoInsertController {
    @Autowired
    private PoInsertService poInsertService;

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse> insertData(PoInsertDto dto) {
        ApiResponse response = poInsertService.insertData(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
