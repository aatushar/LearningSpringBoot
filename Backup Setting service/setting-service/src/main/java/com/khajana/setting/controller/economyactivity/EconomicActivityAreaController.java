package com.khajana.setting.controller.economyactivity;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.economyactivity.EconomicActivityAreaRequestDto;
import com.khajana.setting.service.economyactivity.EconomicActivityAreaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/economic-activity-area")
public class EconomicActivityAreaController {

    @Autowired
    EconomicActivityAreaService economicActivityAreaService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> index(@RequestParam(defaultValue = "0") int pageNo,
                                             @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                             @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                             @RequestParam(defaultValue = "") String filter) {
        ApiResponse apiResponse;
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());
        try {

            var result = economicActivityAreaService.findAllEconomicActivityArea(pageable);

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
            var results = economicActivityAreaService.getDropDown();

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
            var result = economicActivityAreaService.findEconomyActivityAreaById(id);

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
    public ResponseEntity<ApiResponse> addEconomyActivity(@Valid @RequestBody EconomicActivityAreaRequestDto dto) {

        var result = economicActivityAreaService.addEconomicActivityArea(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEconomyActivity(@Valid @PathVariable("id") Long id,
                                                             @Valid @RequestBody EconomicActivityAreaRequestDto dto) {

        var result = economicActivityAreaService.updateEconomyActivity(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

}
