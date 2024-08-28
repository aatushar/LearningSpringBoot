package com.khajana.setting.controller.adjt;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.adjt.OtherAdjtTypeRequestDto;
import com.khajana.setting.service.adjt.OtherAdjtTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/other-adjt-type")
public class OtherAdjtTypeController {
    @Autowired
    private OtherAdjtTypeService otherAdjtTypeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCiommsionerateInfos(@RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                              @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                              @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = otherAdjtTypeService.findAllOtherAdjtTypes(pageable);

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
            var result = otherAdjtTypeService.findOtherAdjtTypeById(id);

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
            var result = otherAdjtTypeService.getDropDown();

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
    public ResponseEntity<ApiResponse> addOtherAdjtType(@Valid @RequestBody OtherAdjtTypeRequestDto dto) {

        var result = otherAdjtTypeService.addOtherAdjtType(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    //    @PutMapping("/update/{id}")
//    public ResponseEntity<ApiResponse> updaiteOtherAdjtType(@PathVariable("id") Long id,
//                                                            @Valid @RequestBody OtherAdjtTypeRequestDto dto) {
//
//        ApiResponse apiResponse;
//        try {
//            var result = otherAdjtTypeService.updateOtherAdjtType(id, dto);
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
    public ResponseEntity<ApiResponse> updateTransSourceType(@Valid @PathVariable("id") Long id,
                                                             @Valid @RequestBody OtherAdjtTypeRequestDto dto) {

        var result = otherAdjtTypeService.updateOtherAdjtType(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleiteOtherAdjtType(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            otherAdjtTypeService.deleteOtherAdjtType(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
