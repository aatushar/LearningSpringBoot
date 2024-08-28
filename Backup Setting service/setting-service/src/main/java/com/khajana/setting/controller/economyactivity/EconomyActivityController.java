package com.khajana.setting.controller.economyactivity;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.economyactivity.EconomyActivityRequestDto;
import com.khajana.setting.service.economyactivity.EconomyActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/economic-activity")
public class EconomyActivityController {

    @Autowired
    EconomyActivityService economyActivityService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> index(@RequestParam(defaultValue = "0") int pageNo,
                                             @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                             @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                             @RequestParam(defaultValue = "") String filter) {
        ApiResponse apiResponse;
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());
        try {

            var result = economyActivityService.findAllEconomyActivitys(pageable);

            if (result.hasContent()) {
                apiResponse = new ApiResponse(200, "Paginated results", result);
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
            var results = economyActivityService.getDropDown();

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
    public ResponseEntity<ApiResponse> getEconomyActivityById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = economyActivityService.findEconomyActivityById(id);

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
    public ResponseEntity<ApiResponse> addEconomyActivity(@Valid @RequestBody EconomyActivityRequestDto dto) {

        var result = economyActivityService.addEconomyActivity(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEconomyActivity(@Valid @PathVariable("id") Long id,
                                                             @Valid @RequestBody EconomyActivityRequestDto dto) {

        var result = economyActivityService.updateEconomyActivity(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

}
