package com.sazzad.artilleryReport.controller.storeDropDown;

import com.sazzad.artilleryReport.service.storeIdDropdown.StoreIdDropDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/settingdev/api/v1/")
public class StoreDropDownController {
    @Autowired
    private StoreIdDropDownService storeIdDropDownService;

    @GetMapping("store-dropdown")
    public ResponseEntity<List<Map<String, Object>>> getStoreDropdown() {
        return ResponseEntity.ok(storeIdDropDownService.getStoreDropdown());
    }
}
