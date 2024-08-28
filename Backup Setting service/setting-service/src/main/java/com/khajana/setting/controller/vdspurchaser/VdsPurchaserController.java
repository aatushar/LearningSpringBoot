package com.khajana.setting.controller.vdspurchaser;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.vdspurchaser.VdsPurchaserRequestDto;
import com.khajana.setting.service.vdspurchaser.VdsPurchaserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/setting/api/v1/vds-purchaser")
public class VdsPurchaserController {

    @Autowired
    private VdsPurchaserService vdsPurchaserService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCiommsionerateInfos(@RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                              @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                              @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = vdsPurchaserService.findAllVdsPurchasers(pageable);

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
            var result = vdsPurchaserService.findVdsPurchaserById(id);

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
    public ResponseEntity<ApiResponse> getDroupDown() {

        ApiResponse apiResponse;
        try {
            var result = vdsPurchaserService.getDropDown();

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

    @GetMapping("/unpaid-vds-purchaser")
    public ResponseEntity<ApiResponse> getUnpaidPaymentHistory() {

        ApiResponse apiResponse;
        try {
            var result = vdsPurchaserService.getUnpaidPaymentHistory();

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
    public ResponseEntity<ApiResponse> addVdsPurchaser(@Valid @RequestBody VdsPurchaserRequestDto dto) {

        var result = vdsPurchaserService.addVdsPurchaser(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updaiteVdsPurchaser(@PathVariable("id") Long id,
                                                           @Valid @RequestBody VdsPurchaserRequestDto dto) {

        ApiResponse apiResponse;
        try {
            var result = vdsPurchaserService.updateVdsPurchaser(id, dto);

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleiteVdsPurchaser(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            vdsPurchaserService.deleteVdsPurchaser(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
