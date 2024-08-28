package com.khajana.setting.controller.ordermanagement.utilizationdeclaration;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.ordermanagement.utilizationdeclaration.UtilizationDeclarationMasterRequestDto;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.repository.ordermanagement.utilizationdeclaration.UtilizationDeclarationMasterRepository;
import com.khajana.setting.service.ordermanagement.utilizationdeclaration.UtilizationDeclarationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setting/api/v1/order/ud")
public class UtilizationDeclarationController {
    @Autowired
    public UtilizationDeclarationMasterRepository utilizationDeclarationMasterRepository;
    @Autowired
    private UtilizationDeclarationService utilizationDeclarationService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> utilizationDeclarations(@RequestParam(defaultValue = "0") int pageNo,
                                                               @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                               @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                               @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = utilizationDeclarationService.findAllUtilizationDeclarationMasters(pageable);

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
    public ResponseEntity<ApiResponse> utilizationDeclarationById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = utilizationDeclarationService.findUtilizationDeclarationMasterById(id);

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
            List<HouseKeeping> result = utilizationDeclarationService.getDropDown();
            java.util.Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(200, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/export-lc-by-customer/{id}")
    public ResponseEntity<ApiResponse> exportLcByCustomer(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = utilizationDeclarationService.exportLcByCustomer(id);
            java.util.Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(200, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBankInfo(@Valid @RequestBody UtilizationDeclarationMasterRequestDto dto) {


        var result = utilizationDeclarationService.addUtilizationDeclarationMaster(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUtilizationDeclarationMaster(@Valid @PathVariable("id") Long id,
                                                                          @Valid @RequestBody UtilizationDeclarationMasterRequestDto dto) {

        var result = utilizationDeclarationService.updateUtilizationDeclarationMaster(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUtilizationDeclarationMaster(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            utilizationDeclarationService.deleteUtilizationDeclarationMaster(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
