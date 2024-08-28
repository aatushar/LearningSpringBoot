package com.khajana.setting.controller.productRequisition;

import com.khajana.setting.dto.productRequisition.ApiResponse;
import com.khajana.setting.service.productRequisition.IndentBossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/indentboss")
public class IndentBossController {

    @Autowired
    private IndentBossService indentBossService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getIndentMasterById(@PathVariable("id") Long id) {
        ApiResponse response = indentBossService.findIndentMasterById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
