package com.sazzad.erpReport.service.PurchaseOrder;

import org.springframework.http.ResponseEntity;

public interface PurchaseOrderService {
    ResponseEntity<byte[]> generatePurchaseOrderReport();
}
