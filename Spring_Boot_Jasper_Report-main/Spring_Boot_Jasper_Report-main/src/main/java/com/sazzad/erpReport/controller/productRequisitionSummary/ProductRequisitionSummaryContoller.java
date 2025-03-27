package com.sazzad.erpReport.controller.productRequisitionSummary;


import com.sazzad.erpReport.service.productRequisitionSummary.ProductRequisitionSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingdev/api/v1")
public class ProductRequisitionSummaryContoller {
    @Autowired
    private ProductRequisitionSummaryService requisitionSummaryService;

    @GetMapping("/product-requisition-summary-report-pdf")
    public ResponseEntity<byte[]> getRequisitionSummaryReport() {
        return requisitionSummaryService.generateRequisitionSummaryReport();
    }
}