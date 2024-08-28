package com.khajana.setting.controller.inventorymethod;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.inventorymethod.InventoryMethodRequestDto;
import com.khajana.setting.service.inventorymethod.InventoryMethodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/inventory-method")
public class InventoryMethodController {

    @Autowired
    InventoryMethodService vatReInventoryMethodService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> index(@RequestParam(defaultValue = "0") int pageNo,
                                             @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                             @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                             @RequestParam(defaultValue = "") String filter) {
        ApiResponse apiResponse;
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());
        try {

            var result = vatReInventoryMethodService.findAllInventoryMethods(pageable);

            if (result.hasContent()) {
                apiResponse = new ApiResponse(200, "Paginated results", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/drop-down")
    public ResponseEntity<ApiResponse> getFiscalYearsDropDown() {

        ApiResponse apiResponse;

        try {
            var results = vatReInventoryMethodService.getDropDown();

            if (results != null) {
                apiResponse = new ApiResponse(200, "Drop Down List", results);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getInventoryMethodById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = vatReInventoryMethodService.findInventoryMethodById(id);

            if (result != null) {
                apiResponse = new ApiResponse(200, "OK", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addInventoryMethod(@Valid @RequestBody InventoryMethodRequestDto dto) {

        var result = vatReInventoryMethodService.addInventoryMethod(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateInventoryMethod(@Valid @PathVariable("id") Long id,
                                                             @Valid @RequestBody InventoryMethodRequestDto dto) {

        var result = vatReInventoryMethodService.updateInventoryMethod(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

}
