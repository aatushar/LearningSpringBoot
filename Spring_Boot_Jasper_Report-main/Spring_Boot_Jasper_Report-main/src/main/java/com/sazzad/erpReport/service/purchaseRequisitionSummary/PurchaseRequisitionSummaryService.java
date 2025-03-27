package com.sazzad.erpReport.service.purchaseRequisitionSummary;

import org.springframework.http.ResponseEntity;

public interface PurchaseRequisitionSummaryService {
    ResponseEntity<byte[]> generatePurchaseRequisitionSummaryReport();
}
