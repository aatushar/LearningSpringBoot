package com.sazzad.erpReport.controller.purchaseRequisition;



import com.sazzad.erpReport.service.purchaseRequisition.PurchaseRequisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingdev/api/v1")
public class PurchaseRequisitionController {

    @Autowired
    private PurchaseRequisitionService purchaseRequisitionService;

    @GetMapping("/purchase-requisition-report-pdf")
    public ResponseEntity<byte[]> getPurchaseRequisitionReportPdf() {
        return purchaseRequisitionService.generatePurchaseRequisition();
    }
}


