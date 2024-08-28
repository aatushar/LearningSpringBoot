package com.khajana.setting.controller.issue;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.service.procedure.HouseKeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setting/api/v1/commercial")
public class IssueReceiveController {

    @Autowired
    private HouseKeepingService houseKeepingService;

    @GetMapping("/vds-seller-init-dropdown")
    public ResponseEntity<ApiResponse> customerHavingIssue() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = houseKeepingService.customerHavingIssue();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/fiscalYearVatMonthFromInputDate")
    public ResponseEntity<ApiResponse> fiscalYearVatMonthFromInputDate(
            @RequestParam("inputDate") java.sql.Date inputDate) {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = houseKeepingService.fiscalYearVatMonthFromInputDate(inputDate);
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

}
