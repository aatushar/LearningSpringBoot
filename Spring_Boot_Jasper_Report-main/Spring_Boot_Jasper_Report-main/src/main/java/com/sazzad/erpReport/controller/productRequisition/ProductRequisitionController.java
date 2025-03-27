package com.sazzad.erpReport.controller.productRequisition;


import com.sazzad.erpReport.service.productRequisition.ProductRequisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class ProductRequisitionController {
    @Autowired
    private ProductRequisitionService productRequisitionService;

    @GetMapping("product-requisition-report-pdf")
    public ResponseEntity<byte[]> getProductRequisition() {
        return productRequisitionService.generateProductRequisition();
    }
}
