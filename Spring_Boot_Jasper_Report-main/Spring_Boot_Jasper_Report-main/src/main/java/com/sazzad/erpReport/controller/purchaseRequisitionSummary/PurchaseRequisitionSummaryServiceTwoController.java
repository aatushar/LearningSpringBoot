package com.sazzad.erpReport.controller.purchaseRequisitionSummary;



import com.sazzad.erpReport.service.purchaseRequisitionSummary.PurchaseRequisitionSummaryServiceTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class PurchaseRequisitionSummaryServiceTwoController {
    @Autowired
    private PurchaseRequisitionSummaryServiceTwo purchaseRequisitionSummaryServiceTwo;

    @GetMapping("/purchase-requisition-summary-report-two-pdf")
    public ResponseEntity<byte[]> getPurchaseRequisitionSummaryTwo() {
        return purchaseRequisitionSummaryServiceTwo.generatePurchaseRequisitionSummaryReportTwo();
    }
}
