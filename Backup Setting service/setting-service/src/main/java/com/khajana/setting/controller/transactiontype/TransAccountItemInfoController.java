package com.khajana.setting.controller.transactiontype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.transactiontype.TransAccountItemInfoRequestDto;
import com.khajana.setting.service.transactiontype.TransAccountItemInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/tran-acc-item")
public class TransAccountItemInfoController {

    @Autowired
    TransAccountItemInfoService transAccountItemInfoService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getItemCat(@RequestParam(defaultValue = "0") int pageNo,
                                                  @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                  @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                  @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = transAccountItemInfoService.findAllTransAccountItem(pageable);

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
    public ResponseEntity<ApiResponse> getTransAccountItemById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = transAccountItemInfoService.getTransAccountItemById(id);

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
            var result = transAccountItemInfoService.getDropDown();

            if (result != null) {
                apiResponse = new ApiResponse(200, "Drop Down List", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTransAccountItems(@Valid @RequestBody TransAccountItemInfoRequestDto dto) {

        var result = transAccountItemInfoService.addTransAccountItem(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    //    @PutMapping("/update/{id}")
//    public ResponseEntity<ApiResponse> updateTransAccountItem(@PathVariable("id") Long id, @Valid @RequestBody TransAccountItemInfoRequestDto dto) {
//
//        ApiResponse apiResponse ;
//        try {
//            var result = transAccountItemInfoService.updateTransAccountItem(id,dto);
//
//            if(result!=null){
//                apiResponse = new ApiResponse(200,"Update Successfully",result);
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
    public ResponseEntity<ApiResponse> updateTransAccountItem(@Valid @PathVariable("id") Long id,
                                                              @Valid @RequestBody TransAccountItemInfoRequestDto dto) {

        var result = transAccountItemInfoService.updateTransAccountItem(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }
}
