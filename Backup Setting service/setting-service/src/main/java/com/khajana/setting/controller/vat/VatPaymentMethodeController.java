package com.khajana.setting.controller.vat;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.vat.VatPaymentMethodeRequestDto;
import com.khajana.setting.dto.vat.VatPaymentMethodeResponseDto;
import com.khajana.setting.service.vat.VatPaymentMethodeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/vat-pay-method")
public class VatPaymentMethodeController {

    @Autowired
    VatPaymentMethodeService vatPaymentMethodeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getTransSourceTypes(@RequestParam(defaultValue = "0") int pageNo,
                                                           @RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                           @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {

            Page<VatPaymentMethodeResponseDto> results = vatPaymentMethodeService.findAllVatPaymentMethodes(pageable);

            if (results.hasContent()) {
                apiResponse = new ApiResponse(200, "Paginated results", results);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getTransSourceTypeById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = vatPaymentMethodeService.findVatPaymentMethodeById(id);

            if (result != null) {
                apiResponse = new ApiResponse(200, "OK ", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/drop-down")
    public ResponseEntity<ApiResponse> getTransSourceTypeDropDown() {

        ApiResponse apiResponse;

        try {
            var results = vatPaymentMethodeService.getDropDown();

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

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addVatRebateType(@Valid @RequestBody VatPaymentMethodeRequestDto dto) {

        var result = vatPaymentMethodeService.addVatPaymentMethode(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateVatRebateType(@Valid @PathVariable("id") Long id,
                                                           @Valid @RequestBody VatPaymentMethodeRequestDto dto) {

        var result = vatPaymentMethodeService.updateVatePaymentMethode(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);

    }
}
