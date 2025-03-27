package com.sazzad.erpReport.controller.itemStockSummary;


import com.sazzad.erpReport.service.itemStockSummary.ItemStockSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class ItemStockSummaryController {

    @Autowired
    public ItemStockSummaryService itemStockSummaryService;


    @GetMapping("/item-stock-summary-pdf")
    public ResponseEntity<byte[]> getItemStockSummary() {
        return itemStockSummaryService.generateItemStockSummaryReport();
    }
}
