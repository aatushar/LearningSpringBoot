package com.khajana.setting.controller.itemcatforretail;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.itemcatforretail.ItemCatForRetailRequestDto;
import com.khajana.setting.service.itemcatforretail.ItemCatForRetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/item-cat-retail")
public class ItemCatForRetailController {

    @Autowired
    ItemCatForRetailService itemCatForRetailService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getItemCat(@RequestParam(defaultValue = "0") int pageNo,
                                                  @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                  @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                  @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = itemCatForRetailService.findAlltemCat(pageable);

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

    @GetMapping("/drop-down")
    public ResponseEntity<ApiResponse> getItemCatDropDown() {

        ApiResponse apiResponse;

        try {
            var results = itemCatForRetailService.getDropDown();

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
    public ResponseEntity<ApiResponse> getItemCatById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = itemCatForRetailService.findItemCatTypeById(id);

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
    public ResponseEntity<ApiResponse> addItemCat(@Valid @RequestBody ItemCatForRetailRequestDto dto) {

        var result = itemCatForRetailService.addItemCatRetail(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateItemCat(@Valid @PathVariable("id") Long id,
                                                     @Valid @RequestBody ItemCatForRetailRequestDto dto) {

        ApiResponse apiResponse;
        try {
            var result = itemCatForRetailService.updatetemCat(id, dto);

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

}
