package com.sazzad.erpReport.service.purchaseOrderSummary;

import org.springframework.http.ResponseEntity;

public interface PurchaseOrderSummaryService {
    ResponseEntity<byte[]> generatePurchaseOrderSummaryReport();
}
