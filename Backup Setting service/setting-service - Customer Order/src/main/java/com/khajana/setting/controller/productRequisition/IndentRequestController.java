package com.khajana.setting.controller.productRequisition;

import com.khajana.setting.dto.productRequisition.ApiResponse;
import com.khajana.setting.dto.productRequisition.IndentRequestDTO;
import com.khajana.setting.service.productRequisition.IndentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/indent")
public class IndentRequestController {
    @Autowired
    private IndentRequestService indentRequestService;

    @PostMapping("/insert")
    public ApiResponse insertData(@Validated @RequestBody IndentRequestDTO dto) {
        return indentRequestService.insertData(dto);
    }
}
