package com.khajana.setting.controller.purchaseOrder;


import com.khajana.setting.dto.productRequisition.CustomPageable;
import com.khajana.setting.dto.purchaseOrder.ApiResponse;
import com.khajana.setting.service.purchaseOrder.PoGetAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/purchaseOrder")
public class PoGetAllController {
    @Autowired
    private PoGetAllService poGetAllService;


    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getPoGetAll(
        @RequestParam("storeId") Long storeId,
        @RequestParam(defaultValue = "id") String dbFieldName,
        @RequestParam(defaultValue = "desc") String sortDirection,
        @RequestParam(defaultValue = "") String filter,
        @RequestParam(defaultValue = "0") int pageNo,
        @RequestParam(defaultValue = "10") int pageSize) {

           CustomPageable pageable = new CustomPageable(pageNo, pageSize, dbFieldName, sortDirection, filter);
           ApiResponse response = poGetAllService.getPoGetAll(storeId, pageable);
           return new ResponseEntity<>(response, HttpStatus.OK);
        }
        @GetMapping("/find/{id}")
        public ResponseEntity <ApiResponse>poGetAllServiceId(@PathVariable("id") Long id) {
            ApiResponse response = poGetAllService.poGetAllListById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }
