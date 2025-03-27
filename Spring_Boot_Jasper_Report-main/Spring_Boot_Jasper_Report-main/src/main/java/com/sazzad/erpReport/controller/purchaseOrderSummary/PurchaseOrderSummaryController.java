package com.sazzad.erpReport.controller.purchaseOrderSummary;


import com.sazzad.erpReport.service.purchaseOrderSummary.PurchaseOrderSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class PurchaseOrderSummaryController {
    @Autowired
    private PurchaseOrderSummaryService purchaseOrderSummaryService;

    @GetMapping("purchase-order-summary-pdf")
    public ResponseEntity<byte[]> getPurchaseOrderSummary() {
        return purchaseOrderSummaryService.generatePurchaseOrderSummaryReport();
    }
}
