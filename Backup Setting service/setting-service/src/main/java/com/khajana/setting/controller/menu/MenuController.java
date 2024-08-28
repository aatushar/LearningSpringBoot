package com.khajana.setting.controller.menu;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.SortField;
import com.khajana.setting.dto.menudto.MenuDto;
import com.khajana.setting.service.menu.MenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/setting/api/v1/menu")
public class MenuController {
    @Autowired
    MenuService menuService;
//    MenuService menuService;

    //    private MenuService MenuService;
//
//    @Autowired
//    public MenuController(MenuService service){
//        this.MenuService = service;
//    }
//
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllData(@RequestParam(defaultValue = "0") int pageNo,
                                                  @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ID") SortField sortField,
                                                  @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                  @RequestParam(defaultValue = "") String filter) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortField.getDbFieldName());
        ApiResponse apiResponse;
        try {

            var result = menuService.findAllMenus(pageable);

            if (result.hasContent()) {
                apiResponse = new ApiResponse(200, "Paginated results", result);
            } else {
                apiResponse = new ApiResponse(404, "No Records Found!", "");
            }

        } catch (Exception e) {
            apiResponse = new ApiResponse(500, e.getMessage(), "error");
        }

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getMenuById(@PathVariable("id") Long id) {

        ApiResponse apiResponse;
        try {
            var result = menuService.findMenuById(id);

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

    //    @PostMapping("/add")
//    public ResponseEntity<ApiResponse> addMenu (@Valid @RequestBody MenuDto dto) {
//        ApiResponse apiResponse;
//        try {
//            var result = menuService.addMenu(dto);
//
//            if (result != null) {
//                apiResponse = new ApiResponse(201, "ok", result);
//            } else {
//                apiResponse = new ApiResponse(404, "No Records Found!", "");
//            }
//
//        } catch (Exception e) {
//            apiResponse = new ApiResponse(500, e.getMessage(), "error");
//        }
//
//        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
//    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMenu(@Valid @RequestBody MenuDto dto) {

        var result = menuService.addMenu(dto);

        return new ResponseEntity<ApiResponse>(result, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateMenu(@Valid @RequestBody MenuDto dto) {

        ApiResponse apiResponse;
        try {
            var result = menuService.updateMenu(dto);

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
