package com.khajana.setting.controller.productRequisition;

import com.khajana.setting.dto.productRequisition.ApiResponse;
import com.khajana.setting.dto.productRequisition.CustomPageable;
import com.khajana.setting.service.productRequisition.IndentMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/indentmaster")
public class IndentMasterController {

    @Autowired
    private IndentMasterService indentService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getIndentMasterList(
            @RequestParam("storeId") Long storeId,
            @RequestParam(defaultValue = "id") String dbFieldName,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        CustomPageable pageable = new CustomPageable(pageNo, pageSize, dbFieldName, sortDirection, filter);
        ApiResponse response = indentService.getIndentMasterList(storeId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> findIndentMasterById(@PathVariable("id") Long id) {
        ApiResponse response = indentService.findIndentMasterById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
