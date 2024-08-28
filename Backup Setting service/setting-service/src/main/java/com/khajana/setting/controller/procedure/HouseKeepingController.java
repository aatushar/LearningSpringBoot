package com.khajana.setting.controller.procedure;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.service.procedure.HouseKeepingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class HouseKeepingController {
    @Autowired
    private HouseKeepingService houseKeepingService;

    @GetMapping("/execute-stored-procedure")
    public ResponseEntity<ApiResponse> executeStoredProcedure() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = houseKeepingService.executeStoredProcedure();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/customer-basic-dropdown")
    public ResponseEntity<ApiResponse> customerDropDown() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = houseKeepingService.customerDropDown();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/company-branch-basic-dropdown")
    public ResponseEntity<ApiResponse> companyDropDown() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = houseKeepingService.companyDropDown();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/vat-structure-dropdown")
    public ResponseEntity<ApiResponse> vatStructureDropDown() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = houseKeepingService.vatStructureDropDown();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }


    @GetMapping("/setting/vat-month-lists-dropdown")
    public ResponseEntity<ApiResponse> vatMonthDropDown() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = houseKeepingService.vatMonthDropDown();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);

    }


    @GetMapping("/setting/company-list-dropdown")
    public ResponseEntity<ApiResponse> companyListDropDown() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = houseKeepingService.companyListDropDown();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }
}
