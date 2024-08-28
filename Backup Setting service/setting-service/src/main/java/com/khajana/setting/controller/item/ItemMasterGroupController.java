package com.khajana.setting.controller.item;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.item.ItemMasterGroupRequestDto;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.service.item.ItemMasterGroupService;
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
@RequestMapping("/setting/api/v1/item-master-group")
public class ItemMasterGroupController {
    @Autowired
    private ItemMasterGroupService itemMasterGroupService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getItemMasterGroups(@RequestParam(defaultValue = "0") int pageNo,
                                                           @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                           @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                           @RequestParam(defaultValue = "") String filter) {

        ApiResponse apiResponse;

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());

        try {
            var results = itemMasterGroupService.findAllItemMasterGroups(filter, pageable);

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
    public ResponseEntity<ApiResponse> getItemMasterGroupById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = itemMasterGroupService.findItemMasterGroupById(id);

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
            List<HouseKeeping> result = itemMasterGroupService.getDropDown();
            Map<String, List<HouseKeeping>> groupedByType = result.stream()
                    .collect(Collectors.groupingBy(HouseKeeping::getType));
            apiResponse = new ApiResponse(201, "ok", groupedByType);
        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBankInfo(@Valid @RequestBody ItemMasterGroupRequestDto dto) {

        var result = itemMasterGroupService.addItemMasterGroup(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateItemMasterGroup(@Valid @PathVariable("id") Long id,
                                                             @Valid @RequestBody ItemMasterGroupRequestDto dto) {

        var result = itemMasterGroupService.updateItemMasterGroup(id, dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteItemMasterGroup(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {

            itemMasterGroupService.deleteItemMasterGroup(id);

            apiResponse = new ApiResponse(200, "ok", "");

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);

    }

}
