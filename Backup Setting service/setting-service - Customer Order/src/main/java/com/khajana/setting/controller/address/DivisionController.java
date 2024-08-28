package com.khajana.setting.controller.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.address.DivisionRequestDto;
import com.khajana.setting.service.address.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setting/api/v1/division")
public class DivisionController {

    private DivisionService divisionService;

    @Autowired
    public DivisionController(DivisionService service){
        this.divisionService = service;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCiommsionerateInfos (
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "ID") SortField sortField,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
            @RequestParam(defaultValue = "") String filter
    ) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = divisionService.findAllDivisions(pageable);

            if (results.hasContent()) {
                apiResponse = new ApiResponse(200, "Paginated results", results);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        }catch (Exception e){
            apiResponse = new ApiResponse(500,e.getMessage(),"");
        }

        return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getCiommsionerateInfoById (@PathVariable("id") Long id) {

        ApiResponse apiResponse ;
        try {
            var result = divisionService.findDivisionById(id);

            if(result!=null){
                apiResponse = new ApiResponse(200,"",result);
            }else{
                apiResponse = new ApiResponse(404,"No Records Found!","");
            }

        }catch (Exception e){
            apiResponse = new ApiResponse(500,e.getMessage(),"");
        }

        return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
    
    @GetMapping("/drop-down")
    public ResponseEntity<ApiResponse> getDroupDown() {

        ApiResponse apiResponse ;
        try {
            var result = divisionService.getDropDown();

            if(result!=null){
                apiResponse = new ApiResponse(200,"",result);
            }else{
                apiResponse = new ApiResponse(404,"No Records Found!","");
            }

        }catch (Exception e){
            apiResponse = new ApiResponse(500,e.getMessage(),"");
        }

        return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }


	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addDivision(@RequestBody DivisionRequestDto dto) {
	
	    var result = divisionService.addDivision(dto);
	
	    return  new ResponseEntity<ApiResponse>(result,HttpStatus.CREATED);
	
	}

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updaiteDivision(@PathVariable("id") Long id, @RequestBody DivisionRequestDto dto) {

        ApiResponse apiResponse ;
        try {
            var result = divisionService.updateDivision(id, dto);

            if(result!=null){
                apiResponse = new ApiResponse(200,"",result);
            }else{
                apiResponse = new ApiResponse(404,"No Records Found!","");
            }
            
        }catch (Exception e){
            apiResponse = new ApiResponse(500,e.getMessage(),"");
        }

        return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleiteDivision(@PathVariable("id") Long id) {

        ApiResponse apiResponse ;
        try {

            divisionService.deleteDivision(id);

            apiResponse = new ApiResponse(200,"Deleted Successfully","");

        }catch (Exception e){
            apiResponse = new ApiResponse(500,e.getMessage(),"");
        }

        return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);

    }

}
