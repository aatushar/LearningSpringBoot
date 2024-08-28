package com.khajana.setting.controller.vatratereference;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.vatratereference.VatRateReferenceReqDto;
import com.khajana.setting.service.vatratereference.VatRateReferenceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/vat-rate-reference")
public class VatRateReferenceController {
    @Autowired
    private VatRateReferenceService vatRateReferenceService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getVatRateReferences(@RequestParam(defaultValue = "0") int pageNo,
                                                            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                            @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = vatRateReferenceService.findAllVatRateReferences(pageable);

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

    @GetMapping("/drop-down")
    public ResponseEntity<ApiResponse> getTransSubTypeDropDown() {

        ApiResponse apiResponse;

        try {
            var results = vatRateReferenceService.getDropDown();

            if (results != null) {
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
    public ResponseEntity<ApiResponse> getVatRateReferenceById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = vatRateReferenceService.findVatRateReferenceById(id);

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
    public ResponseEntity<ApiResponse> addVatRegistrationType(@Valid @RequestBody VatRateReferenceReqDto dto) {

        var result = vatRateReferenceService.addVatRateReference(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateVatRateReference(@Valid @PathVariable("id") Long id,
                                                              @Valid @RequestBody VatRateReferenceReqDto dto) {

        ApiResponse apiResponse;
        try {
            var result = vatRateReferenceService.updateVatRateReference(id, dto);

            if (result != null) {
                apiResponse = new ApiResponse(200, "", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteVatRateReference(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            vatRateReferenceService.deleteVatRateReference(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
