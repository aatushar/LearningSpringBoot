package com.sazzad.erpReport.controller.purchaseRequisitionSummary;


import com.sazzad.erpReport.service.purchaseRequisitionSummary.PurchaseRequisitionSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class PurchaseRequisitionSummaryController {

    @Autowired
    private PurchaseRequisitionSummaryService purchaseRequisitionSummaryService;

    @GetMapping("/purchase-requisition-summary-report-pdf")
    public ResponseEntity<byte[]> getPurchaseRequisitionSummary() {
        return purchaseRequisitionSummaryService.generatePurchaseRequisitionSummaryReport();
    }
}
