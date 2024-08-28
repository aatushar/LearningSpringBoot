package com.khajana.setting.controller.CustomerOrder;

import com.khajana.setting.dto.costomerOrder.ApiResponse;
import com.khajana.setting.dto.costomerOrder.CustomPageable;
import com.khajana.setting.service.customerOrder.CoIndexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/customer-order")
public class CoController {

    private final CoIndexService coIndexService;

    public CoController(CoIndexService coIndexService) {
        this.coIndexService = coIndexService;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getCoIndex(
            @RequestParam("storeId") Long storeId,
            @RequestParam(defaultValue = "id") String dbFieldName,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        CustomPageable pageable = new CustomPageable(pageNo, pageSize, dbFieldName, sortDirection, filter);
        ApiResponse response = coIndexService.getCoIndex(storeId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getCustomerOrderById(@PathVariable("id") Long id) {
        ApiResponse response = coIndexService.coIndexById(id);
        return ResponseEntity.ok(response);
    }
}

