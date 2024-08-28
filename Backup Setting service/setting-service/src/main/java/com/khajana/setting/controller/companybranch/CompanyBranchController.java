package com.khajana.setting.controller.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.CustomResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.companybranch.CompanyBranchRequestDto;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.service.companybranch.CompanyBranchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setting/api/v1/company-branch")
public class CompanyBranchController {
    @Autowired
    private CompanyBranchService companyBranchService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCiommsionerateInfos(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
            @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = companyBranchService.findAllCompanyBranchs(filter, pageable);

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
    public ResponseEntity<ApiResponse> getCiommsionerateInfoById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = companyBranchService.findCompanyBranchById(id);

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

    @GetMapping("/drop-down")
    public ResponseEntity<ApiResponse> companyBranchDropDown() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = companyBranchService.companyBranchDropDown();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCompanyBranch(@Valid @RequestBody CompanyBranchRequestDto dto) {

        var result = companyBranchService.addCompanyBranch(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    // @PutMapping("/update/{id}")
    // public ResponseEntity<ApiResponse> updaiteCompanyBranch(@PathVariable("id")
    // Long id, @Valid @RequestBody CompanyBranchRequestDto dto) {
    //
    // ApiResponse apiResponse ;
    // try {
    // var result = companyBranchService.updateCompanyBranch(id, dto);
    //
    // if(result!=null){
    // apiResponse = new ApiResponse(200,"",result);
    // }else{
    // apiResponse = new ApiResponse(404,"No Records Found!","");
    // }
    //
    // }catch (Exception e){
    // apiResponse = new ApiResponse(500,e.getMessage(),"");
    // }
    //
    // return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    //
    // }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updaiteCompanyBranch(@PathVariable("id") Long id,
            @Valid @RequestBody CompanyBranchRequestDto dto) {

        var result = companyBranchService.updateCompanyBranch(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleiteCompanyBranch(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            companyBranchService.deleteCompanyBranch(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

    // Controller creating using SP Start

    @GetMapping("/find-by-sp")
    public CustomResponse getAllSellIndexData(@RequestParam("id") int spId) {
        return companyBranchService.getAllCompanyBrunchIndexByIdUsingSp(spId);
    }

    @GetMapping("/drop-down-by-sp")
    public ResponseEntity<ApiResponse> customerDropDown() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = companyBranchService.getAllCompanyBrunchIndexDropdownUsingSp();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, " found data", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "not data");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/all-by-sp")
    public CustomResponse getVdsPurchaserIndexList(
            @RequestParam(name = "sortField", required = false) String sortField,
            @RequestParam(name = "sortOrder", defaultValue = "ASC") String sortOrder) {
        CustomResponse response = companyBranchService.getAllCompanyBrunchIndexListUsingSp(sortField, sortOrder);

        return response;
    }

    // Controller creating using SP End

}
