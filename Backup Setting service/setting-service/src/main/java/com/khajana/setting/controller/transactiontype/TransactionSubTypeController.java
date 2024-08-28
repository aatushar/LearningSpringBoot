package com.khajana.setting.controller.transactiontype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.transactiontype.TransactionSubTypeRequestDto;
import com.khajana.setting.service.transactiontype.TransactionSubTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping({"/setting/api/v1/tran-sub-type", "/setting/api/v1/transSubType"})
public class TransactionSubTypeController {

    @Autowired
    TransactionSubTypeService transactionSubTypeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getTransSourceTypes(@RequestParam(defaultValue = "0") int pageNo,
                                                           @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                           @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                           @RequestParam(defaultValue = "") String filter) {
        ApiResponse apiResponse;
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var result = transactionSubTypeService.findAllTransactionSubTypes(pageable);

            if (result != null) {
                apiResponse = new ApiResponse(200, "Paginated Results", result);
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
            var results = transactionSubTypeService.getTransactionSubTypeDropDown();

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
    public ResponseEntity<ApiResponse> getTransSourceTypeById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = transactionSubTypeService.findTransactionSubTypeById(id);

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
    public ResponseEntity<ApiResponse> addTransTransSubType(@Valid @RequestBody TransactionSubTypeRequestDto dto) {

        var result = transactionSubTypeService.addTransactionSubType(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateTransSourceType(@Valid @PathVariable("id") Long id,
                                                             @Valid @RequestBody TransactionSubTypeRequestDto dto) {

        var result = transactionSubTypeService.updateTransactionSubType(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);

    }

}
