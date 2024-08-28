package com.khajana.setting.controller.vat;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.vat.VatMonthRequestDto;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.service.vat.VatMonthService;
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
@RequestMapping("/setting/api/v1/vat-month")
public class VatMonthController {
    @Autowired
    private VatMonthService vatmonthService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllFinancialYearInfo(@RequestParam(defaultValue = "0") int pageNo,
                                                               @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                               @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                               @RequestParam(defaultValue = "") String filter) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        var result = vatmonthService.findAllVatMonths(pageable);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);

    }

    @GetMapping("/dropdown")
    public ResponseEntity<ApiResponse> vatMonthDropDown() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = vatmonthService.vatMonthDropDown();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getVatRegistrationTypeById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = vatmonthService.findVatMonthById(id);

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

    @GetMapping("/find/fy/{id}")
    public ResponseEntity<ApiResponse> getVatRegistrationTypeByFyId(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = vatmonthService.findVatMonthById(id);

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
    public ResponseEntity<ApiResponse> addVatRegistrationType(@Valid @RequestBody VatMonthRequestDto dto) {

        var result = vatmonthService.addVatVatMonth(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateVatRegistrationType(@Valid @PathVariable("id") Long id,
                                                                 @Valid @RequestBody VatMonthRequestDto dto) {

        var result = vatmonthService.updateVatMonth(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

}
