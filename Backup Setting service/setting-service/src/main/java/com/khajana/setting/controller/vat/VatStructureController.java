package com.khajana.setting.controller.vat;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.vat.VatStructureParentRequestDto;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.service.procedure.HouseKeepingService;
import com.khajana.setting.service.vat.VatStructureService;
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
@RequestMapping("/setting/api/v1/vat-structure")
public class VatStructureController {

    @Autowired
    VatStructureService vatReVatStructureService;

    @Autowired
    HouseKeepingService houseKeepingService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> index(@RequestParam(defaultValue = "0") int pageNo,
                                             @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                             @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                             @RequestParam(defaultValue = "") String filter) {
        ApiResponse apiResponse;
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());
        try {

            var result = vatReVatStructureService.findAllVatStructures(pageable);

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
    public ResponseEntity<ApiResponse> vatStructureDropDown() {
        ApiResponse apiResponse;
        try {
            List<HouseKeeping> result = houseKeepingService.vatStructureDropDown();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getVatStructureById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = vatReVatStructureService.findVatStructureById(id);

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

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addVatStructure(@Valid @RequestBody VatStructureParentRequestDto dto) {

        var result = vatReVatStructureService.addVatStructure(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateVatStructure(@PathVariable("id") Long id,
                                                          @Valid @RequestBody VatStructureParentRequestDto dto) {

        var result = vatReVatStructureService.updateVatStructure(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

}
