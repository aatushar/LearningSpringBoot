package com.khajana.setting.controller.companybranch;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.companybranch.CompanyBranchEconomicActivityAreaRequestDto;
import com.khajana.setting.service.companybranch.CompanyBranchEconomicActivityAreaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setting/api/v1/company-branch-economic-activity-area")
public class CompanyBranchEconomicActivityAreaController {
    @Autowired
    private CompanyBranchEconomicActivityAreaService companyBranchEconomicActivityAreaService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCiommsionerateInfos(@RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                              @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                              @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = companyBranchEconomicActivityAreaService.findAllCompanyBranchEconomicActivityAreas(pageable);

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
            var result = companyBranchEconomicActivityAreaService.findCompanyBranchEconomicActivityAreaById(id);

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
    public ResponseEntity<ApiResponse> getDroupDown() {

        ApiResponse apiResponse;
        try {
            var result = companyBranchEconomicActivityAreaService.getDropDown();

            if (result != null) {
                apiResponse = new ApiResponse(200, "Drop Down List", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCompanyBranchEconomicActivityArea(@Valid @RequestBody CompanyBranchEconomicActivityAreaRequestDto dto) {

        var result = companyBranchEconomicActivityAreaService.addCompanyBranchEconomicActivityArea(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    //    @PutMapping("/update/{id}")
//    public ResponseEntity<ApiResponse> updaiteCompanyBranchEconomicActivityArea(@PathVariable("id") Long id, @Valid @RequestBody CompanyBranchEconomicActivityAreaRequestDto dto) {
//
//        ApiResponse apiResponse ;
//        try {
//            var result = companyBranchEconomicActivityAreaService.updateCompanyBranchEconomicActivityArea(id, dto);
//
//            if(result!=null){
//                apiResponse = new ApiResponse(200,"",result);
//            }else{
//                apiResponse = new ApiResponse(404,"No Records Found!","");
//            }
//
//        }catch (Exception e){
//            apiResponse = new ApiResponse(500,e.getMessage(),"");
//        }
//
//        return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
//
//    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updaiteCompanyBranchEconomicActivityArea(@PathVariable("id") Long id,
                                                                                @Valid @RequestBody CompanyBranchEconomicActivityAreaRequestDto dto) {

        var result = companyBranchEconomicActivityAreaService.updateCompanyBranchEconomicActivityArea(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleiteCompanyBranchEconomicActivityArea(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            companyBranchEconomicActivityAreaService.deleteCompanyBranchEconomicActivityArea(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
