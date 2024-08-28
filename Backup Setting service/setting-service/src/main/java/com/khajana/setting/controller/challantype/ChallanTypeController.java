package com.khajana.setting.controller.challantype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.challantype.ChallanTypeRequestDto;
import com.khajana.setting.service.challantype.ChallanTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/challan-type")
public class ChallanTypeController {

    @Autowired
    ChallanTypeService challanTypeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getItemCat(@RequestParam(defaultValue = "0") int pageNo,
                                                  @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                  @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                  @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = challanTypeService.findAllChallanTypes(pageable);

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
    public ResponseEntity<ApiResponse> getItemCatById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = challanTypeService.findChallanTypeById(id);

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
    public ResponseEntity<ApiResponse> addTransTransSubType(@Valid @RequestBody ChallanTypeRequestDto dto) {

        var result = challanTypeService.addChallanTypes(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }
//    @PostMapping("/add")
//    public ResponseEntity<ApiResponse> addTransSourceType(@Valid @RequestBody ChallanTypeDto dto) {
//
//        ApiResponse apiResponse ;
//        try {
//            var result = challanTypeService.addChallanTypes(dto);
//
//            if(result!=null){
//                apiResponse = new ApiResponse(201,"",result);
//            }else{
//                apiResponse = new ApiResponse(404,"No Records Found!","");
//            }
//
//        }catch (Exception e){
//            apiResponse = new ApiResponse(500,e.getMessage(),"");
//        }
//
//        return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.CREATED);
//
//    }

    // @PutMapping("/update/{id}")
//    public ResponseEntity<ApiResponse> updateTransSourceType(@Valid @RequestBody ChallanTypeRequestDto dto) {
//
//        ApiResponse apiResponse ;
//        try {
//            var result = challanTypeService.updateChallanTypes(dto);
//
//            if(result!=null){
//                apiResponse = new ApiResponse(200,"",result);
//            }else{
//                apiResponse = new ApiResponse(404,"No Records Found!","");
//            }
//
//        }catch (Exception e){
//            apiResponse = new ApiResponse(500,e.getMessage(),"");
//        }
//
//        return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
//
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateTransSourceType(@PathVariable("id") Long id,
                                                             @Valid @RequestBody ChallanTypeRequestDto dto) {

        var result = challanTypeService.updateChallanTypes(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }
//@PostMapping("/add")
//public ResponseEntity<ApiResponse> updateTransSourceType(@Valid @RequestBody ChallanTypeDto dto) {
//
//    var result = challanTypeService.updateChallanTypes(dto);
//
//    return  new ResponseEntity<ApiResponse>(result,HttpStatus.CREATED);
//
//}

}
