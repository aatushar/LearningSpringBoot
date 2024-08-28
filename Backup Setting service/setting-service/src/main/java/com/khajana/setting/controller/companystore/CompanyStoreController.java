package com.khajana.setting.controller.companystore;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.companystore.CompanyStoreRequestDto;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.service.companystore.CompanyStoreService;
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
@RequestMapping("/setting/api/v1/company-store")
public class CompanyStoreController {
    @Autowired
    private CompanyStoreService companyStoreService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCiommsionerateInfos(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
            @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = companyStoreService.findAllCompanyStores(filter, pageable);

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
            var result = companyStoreService.findCompanyStoreById(id);

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
            var result = companyStoreService.getDropDown();

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

    @GetMapping("/basic-dropdown")
    public ResponseEntity<ApiResponse> companyStoreDropDown() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = companyStoreService.companyStoreDropDown();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(200, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCompanyStore(@Valid @RequestBody CompanyStoreRequestDto dto) {

        var result = companyStoreService.addCompanyStore(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    // @PutMapping("/update/{id}")
    // public ResponseEntity<ApiResponse> updaiteCompanyStore(@PathVariable("id")
    // Long id, @Valid @RequestBody CompanyStoreRequestDto dto) {
    //
    // ApiResponse apiResponse ;
    // try {
    // var result = companyStoreService.updateCompanyStore(id, dto);
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
    public ResponseEntity<ApiResponse> updaiteCompanyStore(@PathVariable("id") Long id,
            @Valid @RequestBody CompanyStoreRequestDto dto) {

        var result = companyStoreService.updateCompanyStore(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleiteCompanyStore(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            companyStoreService.deleteCompanyStore(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
