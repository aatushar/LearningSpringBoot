package com.khajana.setting.controller.CustomerOrder;

import com.khajana.setting.dto.costomerOrder.ApiResponse;
import com.khajana.setting.dto.costomerOrder.CustomPageable;
import com.khajana.setting.service.customerOrder.DeliveryIndexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/customer-delivery")
public class DeliveryIndexController {
    private final DeliveryIndexService deliveryIndexService;

    public DeliveryIndexController(DeliveryIndexService deliveryIndexService) {
        this.deliveryIndexService = deliveryIndexService;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getDeliveryIndex(
            @RequestParam("storeId") Long storeId,
            @RequestParam(defaultValue = "id") String dbFieldName,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {

        CustomPageable pageable = new CustomPageable(pageNo, pageSize, dbFieldName, sortDirection, filter);
        ApiResponse response = deliveryIndexService.getDeliveryIndex(storeId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> deliveryIndexById(@PathVariable("id") Long id) {
        ApiResponse response = deliveryIndexService.deliveryIndexById(id);
        return ResponseEntity.ok(response);
    }
}
