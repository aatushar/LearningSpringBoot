package com.khajana.setting.controller.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.address.CityRequestDto;
import com.khajana.setting.service.address.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/city")
public class CityController {

    @Autowired
    private CityService cityService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCiommsionerateInfos(@RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                              @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                              @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = cityService.findAllCitys(pageable);

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
            var result = cityService.findCityById(id);

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
            var result = cityService.getDropDown();

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
    public ResponseEntity<ApiResponse> addCity(@Valid @RequestBody CityRequestDto dto) {

        var result = cityService.addCity(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    //    @PutMapping("/update/{id}")
//    public ResponseEntity<ApiResponse> updaiteCity(@PathVariable("id") Long id,
//                                                   @Valid @RequestBody CityRequestDto dto) {
//
//        ApiResponse apiResponse;
//        try {
//            var result = cityService.updateCity(id, dto);
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
    public ResponseEntity<ApiResponse> updaiteCity(@Valid @PathVariable("id") Long id,
                                                   @Valid @RequestBody CityRequestDto dto) {

        var result = cityService.updateCity(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleiteCity(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            cityService.deleteCity(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
