package com.khajana.setting.controller.purchaseRequsition;

import com.khajana.setting.dto.productRequisition.ApiResponse;
import com.khajana.setting.dto.productRequisition.CustomPageable;
import com.khajana.setting.service.purchaseRequsiton.DemoMasterListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/master")
public class DemoMasterListController {

    @Autowired
    private DemoMasterListService demoMasterListService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getDemoMasterList(
            @RequestParam("storeId") Long storeId,
            @RequestParam(defaultValue = "id") String dbFieldName,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        CustomPageable pageable = new CustomPageable(pageNo, pageSize, dbFieldName, sortDirection, filter);
        ApiResponse response = demoMasterListService.getDemoMasterList(storeId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> findDemoMasterListById(@PathVariable("id") Long id) {
        ApiResponse response = demoMasterListService.demoMasterListById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
