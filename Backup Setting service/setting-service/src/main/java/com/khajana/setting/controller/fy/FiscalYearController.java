package com.khajana.setting.controller.fy;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.fy.FiscalYearInfoRequestDto;
import com.khajana.setting.service.fy.FiscalYearService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/fiscal-year")
public class FiscalYearController {

    @Autowired
    private FiscalYearService fiscalYearService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllFiscalYears(@RequestParam(defaultValue = "0") int pageNo,
                                                         @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                         @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                         @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = fiscalYearService.findAllFiscalYear(pageable);

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
    public ResponseEntity<ApiResponse> getFiscalYearsDropDown() {

        ApiResponse apiResponse;

        try {
            var results = fiscalYearService.getDropDown();

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
    public ResponseEntity<ApiResponse> getFiscalYearById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = fiscalYearService.findFiscalYearById(id);

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
    public ResponseEntity<ApiResponse> addFiscalYearType(@Valid @RequestBody FiscalYearInfoRequestDto dto) {

        var result = fiscalYearService.addFiscalYear(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    //    @PutMapping("/update/{id}")
//    public ResponseEntity<ApiResponse> updateFiscalYear(@PathVariable("id") Long id, @Valid @RequestBody FiscalYearInfoRequestDto dto) {
//
//        ApiResponse apiResponse ;
//        try {
//            var result = fiscalYearService.updateFiscalYear(id, dto);
//
//            if(result!=null){
//                apiResponse = new ApiResponse(200,"Fiscal Year successfully Updated",result);
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
    public ResponseEntity<ApiResponse> updateFiscalYear(@Valid @PathVariable("id") Long id,
                                                        @Valid @RequestBody FiscalYearInfoRequestDto dto) {

        var result = fiscalYearService.updateFiscalYear(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

}
