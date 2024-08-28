package com.khajana.setting.controller.transactiontype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.transactiontype.TransactionSourceTypeRequestDto;
import com.khajana.setting.service.transactiontype.TransactionSourceTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/tran-source-type")
public class TransactionSourceTypeController {
    @Autowired
    private TransactionSourceTypeService transactionSourceTypeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getTransSourceTypes(@RequestParam(defaultValue = "0") int pageNo,
                                                           @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                           @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                           @RequestParam(defaultValue = "") String filter) {

        // ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        var results = transactionSourceTypeService.findAllTransactionSourceTypes(pageable);

        /*
         * try { var results =
         * transactionSourceTypeService.findAllTransactionSourceTypes(pageable);
         *
         * if (results.hasContent()) { apiResponse = new ApiResponse(200,
         * "Paginated results", results); } else { apiResponse = new ApiResponse(404,
         * "No Records Found!", ""); }
         *
         * }catch (Exception e){ apiResponse = new ApiResponse(500,e.getMessage(),""); }
         */

        return new ResponseEntity<ApiResponse>(results, HttpStatus.OK);
    }

    @GetMapping("/drop-down")
    public ResponseEntity<ApiResponse> getTransSourceTypeDropDown() {

        ApiResponse apiResponse;

        try {
            var results = transactionSourceTypeService.getDropDown();

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
            var result = transactionSourceTypeService.findTransactionSourceTypeById(id);

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
    public ResponseEntity<ApiResponse> addTransSourceType(@Valid @RequestBody TransactionSourceTypeRequestDto dto) {

        var result = transactionSourceTypeService.addTransactionSourceType(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateTransSourceType(@Valid @PathVariable("id") Long id,
                                                             @Valid @RequestBody TransactionSourceTypeRequestDto dto) {

        var result = transactionSourceTypeService.updateTransactionSourceType(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteTransSourceType(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            transactionSourceTypeService.deleteTransactionSourceType(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
