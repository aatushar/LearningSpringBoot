package com.khajana.setting.controller.policestation;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.policestation.PoliceStationsRequestDto;
import com.khajana.setting.service.policestation.PoliceStationServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/police")
public class PoliceStationControllers {
    @Autowired
    PoliceStationServices policeStationService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getTransSourceTypes(@RequestParam(defaultValue = "0") int pageNo,
                                                           @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                           @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                           @RequestParam(defaultValue = "") String filter) {
        ApiResponse apiResponse;
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var result = policeStationService.findAllPoliceStation(pageable);

            if (result != null) {
                apiResponse = new ApiResponse(200, "Paginated Results", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/drop-down")
    public ResponseEntity<ApiResponse> getTransSourceTypes(

    ) {

        ApiResponse apiResponse;

        try {
            var results = policeStationService.getDropDown();

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
    public ResponseEntity<ApiResponse> getTransSourceTypeById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = policeStationService.findPoliceStationById(id);

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
    public ResponseEntity<ApiResponse> addTransactionTransSubType(@Valid @RequestBody PoliceStationsRequestDto dto) {

        var result = policeStationService.addPoliceStation(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateTransSourceType(@Valid @PathVariable("id") Long id,
                                                             @Valid @RequestBody PoliceStationsRequestDto dto) {

        var result = policeStationService.updatePoliceStation(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);

    }

//    @PutMapping("/update-status/{id}")
//    public ResponseEntity<ApiResponse> updateTransSourceTypeStatus(@Valid @PathVariable("id") Long id,
//                                                                   @Valid @RequestBody TransactionTypeActiveRequestDto dto) {
//
//        var result = transactionTypeService.updateTransactionTypeStatus(id, dto);
//
//        return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
//
//    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<ApiResponse> deleteTransSourceType(@PathVariable("id") Long id) {
//
//        ApiResponse apiResponse;
//        try {
//
//            transactionTypeService.deleteTransactionType(id);
//
//            apiResponse = new ApiResponse(200, "ok", "");
//
//        } catch (Exception e) {
//            apiResponse = new ApiResponse(500, e.getMessage(), "error");
//        }
//
//        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
//
//    }

}
