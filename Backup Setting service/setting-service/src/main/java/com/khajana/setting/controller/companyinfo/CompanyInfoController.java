package com.khajana.setting.controller.companyinfo;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.companybranch.CompanyRequestDto;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.service.companybranch.CompanyService;
import com.khajana.setting.service.companyinfo.CompanyInfoService;
import com.khajana.setting.service.procedure.HouseKeepingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setting/api/v1/company")
public class CompanyInfoController {
    @Autowired
    CompanyInfoService companyInfoService;

    @Autowired
    CompanyService companyService;

    @Autowired
    HouseKeepingService houseKeepingService;

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> findCompanyById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = companyService.findCompanyById(id);

            if (result != null) {
                apiResponse = new ApiResponse(200, "CompanyInfo Found", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/drop-down")
    public ResponseEntity<ApiResponse> companyInfoDropDown() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = companyInfoService.companyInfoDropDown();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, " found data", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "not data");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCiommsionerateInfos(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
            @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = companyService.findAllCompanys(filter, pageable);

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

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCompany(@Valid @ModelAttribute CompanyRequestDto dto) throws IOException {

        var result = companyService.addCompany(dto);
        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCompany(@PathVariable("id") Long id,
            @Valid @ModelAttribute CompanyRequestDto dto) throws IOException {

        dto.setCompLogo(null);

        var result = companyService.updateCompany(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }
}
