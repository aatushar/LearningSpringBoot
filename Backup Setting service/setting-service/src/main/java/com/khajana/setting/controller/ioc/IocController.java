package com.khajana.setting.controller.ioc;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.ioc.IocRequestDto;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.service.ioc.IocService;
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
@RequestMapping("/setting/api/v1/ioc")
public class IocController {

    @Autowired
    private IocService iocService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getIocs(@RequestParam(defaultValue = "0") int pageNo,
                                               @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                               @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                               @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = iocService.findAllIocs(pageable);

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
    public ResponseEntity<ApiResponse> getIocById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = iocService.findIocById(id);

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
    public ResponseEntity<ApiResponse> getDropDown() {

        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = iocService.getDropDown();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(200, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/drop-down-product-type-uom/{ItemInfoId}")
    public ResponseEntity<ApiResponse> iocProductTypeUomDropdown(@PathVariable Long ItemInfoId) {

        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = iocService.iocProductTypeUomDropdown(ItemInfoId);
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(200, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBankInfo(@Valid @RequestBody IocRequestDto dto) {

        var result = iocService.addIoc(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateIoc(@Valid @PathVariable("id") Long id,
                                                 @Valid @RequestBody IocRequestDto dto) {

        var result = iocService.updateIoc(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteIoc(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            iocService.deleteIoc(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
