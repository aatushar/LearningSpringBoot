package com.khajana.setting.controller.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.address.CountryRequestDto;
import com.khajana.setting.service.address.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/country")
public class CountryController {
    @Autowired
    private CountryService countryService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCiommsionerateInfos(@RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                              @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                              @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = countryService.findAllCountrys(pageable);

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
            var result = countryService.findCountryById(id);

            if (result != null) {
                apiResponse = new ApiResponse(200, "ok", result);
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
            var result = countryService.getDropDown();

            if (result != null) {
                apiResponse = new ApiResponse(200, "", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCountry(@Valid @RequestBody CountryRequestDto dto) {

        var result = countryService.addCountry(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    //    @PutMapping("/update/{id}")
//    public ResponseEntity<ApiResponse> updaiteCountry(@PathVariable("id") Long id,
//                                                      @Valid @RequestBody CountryRequestDto dto) {
//
//        ApiResponse apiResponse;
//        try {
//            var result = countryService.updateCountry(id, dto);
//
//            if (result != null) {
//                apiResponse = new ApiResponse(200, "ok", result);
//            } else {
//                apiResponse = new ApiResponse(404, "No Records Found!", "");
//            }
//
//        } catch (Exception e) {
//            apiResponse = new ApiResponse(500, e.getMessage(), "error");
//        }
//
//        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
//
//    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateVatRateType(@Valid @PathVariable("id") Long id,
                                                         @Valid @RequestBody CountryRequestDto dto) {

        var result = countryService.updateCountry(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleiteCountry(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            countryService.deleteCountry(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
