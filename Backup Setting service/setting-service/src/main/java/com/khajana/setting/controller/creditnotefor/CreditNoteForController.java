package com.khajana.setting.controller.creditnotefor;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.creditnotefor.CreditNoteForRequestDto;
import com.khajana.setting.service.creditnotefor.CreditNoteForService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setting/api/v1/credit-note-for")
public class CreditNoteForController {
    @Autowired
    private CreditNoteForService creditNoteForService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCiommsionerateInfos(@RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                              @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                              @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = creditNoteForService.findAllCreditNoteFors(pageable);

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
            var result = creditNoteForService.findCreditNoteForById(id);

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
            var result = creditNoteForService.getDropDown();

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
    public ResponseEntity<ApiResponse> addCreditNoteFor(@Valid @RequestBody CreditNoteForRequestDto dto) {

        var result = creditNoteForService.addCreditNoteFor(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    //    @PutMapping("/update/{id}")
//    public ResponseEntity<ApiResponse> updaiteCreditNoteFor(@PathVariable("id") Long id, @Valid @RequestBody CreditNoteForRequestDto dto) {
//
//        ApiResponse apiResponse ;
//        try {
//            var result = creditNoteForService.updateCreditNoteFor(id, dto);
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
    public ResponseEntity<ApiResponse> updaiteCreditNoteFor(@PathVariable("id") Long id,
                                                            @Valid @RequestBody CreditNoteForRequestDto dto) {

        var result = creditNoteForService.updateCreditNoteFor(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleiteCreditNoteFor(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            creditNoteForService.deleteCreditNoteFor(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
