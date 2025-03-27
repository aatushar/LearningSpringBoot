package com.sazzad.erpReport.controller.itemOpeningStockReport;


import com.sazzad.erpReport.service.itemOpeningStockReport.ItemOpeningStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class ItemOpeningStockController {
    @Autowired
    private ItemOpeningStockService itemOpeningStockService;


    @GetMapping("/item-opening-stock-report-pdf")
    public ResponseEntity<byte[]> getItemOpeningStockReport() {
        return itemOpeningStockService.generateItemOpeningStockReport();
    }
}
