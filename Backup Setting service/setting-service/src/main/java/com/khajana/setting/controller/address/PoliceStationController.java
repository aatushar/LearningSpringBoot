package com.khajana.setting.controller.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.address.PoliceStationRequestDto;
import com.khajana.setting.service.address.PoliceStationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/police-station")
public class PoliceStationController {
    @Autowired
    private PoliceStationService policeStationService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCiommsionerateInfos(@RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                              @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                              @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = policeStationService.findAllPoliceStations(pageable);

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
            var result = policeStationService.findPoliceStationById(id);

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
            var result = policeStationService.getDropDown();

            if (result != null) {
                apiResponse = new ApiResponse(200, "Ok", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addPoliceStation(@Valid @RequestBody PoliceStationRequestDto dto) {

        var result = policeStationService.addPoliceStation(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updaitePoliceStation(@PathVariable("id") Long id,
                                                            @Valid @RequestBody PoliceStationRequestDto dto) {

        ApiResponse apiResponse;
        try {
            var result = policeStationService.updatePoliceStation(id, dto);

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleitePoliceStation(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            policeStationService.deletePoliceStation(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
