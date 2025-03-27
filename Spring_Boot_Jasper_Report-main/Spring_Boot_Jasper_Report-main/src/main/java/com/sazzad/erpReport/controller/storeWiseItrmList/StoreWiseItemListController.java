package com.sazzad.erpReport.controller.storeWiseItrmList;

import com.sazzad.erpReport.service.storeWiseItemList.StoreWiseItemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class StoreWiseItemListController {

    @Autowired
    private StoreWiseItemListService storeWiseItemListService;

    @GetMapping("/store-wise-item-list-report-pdf")
    public ResponseEntity<byte[]> getStoreWiseItemList() {
        return storeWiseItemListService.generateStoreWiseItemListReport();
    }
}
