package com.khajana.setting.controller.vat;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.vat.VatRebateTypeRequestDto;
import com.khajana.setting.service.vat.VatRebateTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/vat-rebate-type")
public class VatRebateTypeController {

    @Autowired
    VatRebateTypeService vatReVatRebateTypeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> index(@RequestParam(defaultValue = "0") int pageNo,
                                             @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                             @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                             @RequestParam(defaultValue = "") String filter) {
        ApiResponse apiResponse;
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());
        try {

            var result = vatReVatRebateTypeService.findAllVatRebateTypes(pageable);

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

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchByName(@RequestParam String name) {
        ApiResponse apiResponse;

        try {
            var result = vatReVatRebateTypeService.findByVatRebateName(name);

            if (result != null && !result.isEmpty()) {
                apiResponse = new ApiResponse(200, "Search results", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @GetMapping("/drop-down")
    public ResponseEntity<ApiResponse> getFiscalYearsDropDown() {

        ApiResponse apiResponse;

        try {
            var results = vatReVatRebateTypeService.getDropDown();

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
    public ResponseEntity<ApiResponse> getVatRebateTypeById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = vatReVatRebateTypeService.findVatRebateTypeById(id);

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
    public ResponseEntity<ApiResponse> addVatRebateType(@Valid @RequestBody VatRebateTypeRequestDto dto) {

        var result = vatReVatRebateTypeService.addVatRebateType(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateVatRebateType(@Valid @PathVariable("id") Long id,
                                                           @Valid @RequestBody VatRebateTypeRequestDto dto) {

        var result = vatReVatRebateTypeService.updateVatRebateType(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

}
