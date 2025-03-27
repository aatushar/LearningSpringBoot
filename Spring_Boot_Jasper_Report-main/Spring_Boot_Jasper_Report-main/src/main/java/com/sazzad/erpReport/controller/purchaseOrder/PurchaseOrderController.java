package com.sazzad.erpReport.controller.purchaseOrder;

import com.sazzad.erpReport.service.PurchaseOrder.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping("/purchase-order-pdf")
    public ResponseEntity<byte[]> getPurchaseOrder() {
        return purchaseOrderService.generatePurchaseOrderReport();
    }
}
