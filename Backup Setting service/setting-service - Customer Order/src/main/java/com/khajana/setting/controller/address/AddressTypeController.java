package com.khajana.setting.controller.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.address.AddressTypeRequestDto;
import com.khajana.setting.service.address.AddressTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setting/api/v1/address-type")
public class AddressTypeController {

    private AddressTypeService addressTypeService;

    @Autowired
    public AddressTypeController(AddressTypeService service){
        this.addressTypeService = service;
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
            var results = addressTypeService.findAllAddressTypes(pageable);

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
            var result = addressTypeService.findAddressTypeById(id);

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
            var result = addressTypeService.getDropDown();

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
	public ResponseEntity<ApiResponse> addAddressType(@RequestBody AddressTypeRequestDto dto) {
	
	    var result = addressTypeService.addAddressType(dto);
	
	    return  new ResponseEntity<ApiResponse>(result,HttpStatus.CREATED);
	
	}

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updaiteAddressType(@PathVariable("id") Long id, @RequestBody AddressTypeRequestDto dto) {

        ApiResponse apiResponse ;
        try {
            var result = addressTypeService.updateAddressType(id, dto);

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
    public ResponseEntity<ApiResponse> deleiteAddressType(@PathVariable("id") Long id) {

        ApiResponse apiResponse ;
        try {

            addressTypeService.deleteAddressType(id);

            apiResponse = new ApiResponse(200,"Deleted Successfully","");

        }catch (Exception e){
            apiResponse = new ApiResponse(500,e.getMessage(),"");
        }

        return  new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);

    }

}
