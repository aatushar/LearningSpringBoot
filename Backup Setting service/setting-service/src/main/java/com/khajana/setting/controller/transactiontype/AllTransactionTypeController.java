package com.khajana.setting.controller.transactiontype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.BranchWiseStore;
import com.khajana.setting.dto.DBCommonInsertAck;
import com.khajana.setting.dto.TransactionTypeRequest;
import com.khajana.setting.dto.transactiontype.TransactionSubTypeResponseDto;
import com.khajana.setting.service.transactiontype.AllTransactionTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/setting/api/v1/allTransType")
public class AllTransactionTypeController {
    @Autowired
    private AllTransactionTypeService allTransactionTypeService;

    /* Temporary used it , later it will be removed */
    @GetMapping("/subType")
    public ResponseEntity<ApiResponse> getTransSubTypes(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "0", required = false) int pageSize) {
        ApiResponse apiResponse;
        try {
            List<TransactionSubTypeResponseDto> results = allTransactionTypeService.getAllTransactionSubTypes(0, 0);
            if (results != null) {
                apiResponse = new ApiResponse(200, "", results);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<DBCommonInsertAck> insertTransactionSubType(@Valid @RequestBody TransactionTypeRequest dto) {

        return new ResponseEntity<>(allTransactionTypeService.insertTransactionType(dto.getSourceTypeId(),
                dto.getTrnsTypeNameEn(), dto.getTrnsTypeNameBn()), HttpStatus.CREATED);
    }

    @GetMapping("/branchWiseStore")
    public ResponseEntity<ApiResponse> getCompanyInfo(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "0", required = false) int pageSize) {

        ApiResponse apiResponse;
        try {
            List<BranchWiseStore> results = allTransactionTypeService.getAllBranchWiseStores(0, 0);
            if (results != null) {
                apiResponse = new ApiResponse(200, "OK", results);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
