package com.sazzad.erpReport.service.purchaseRequisition;

import org.springframework.http.ResponseEntity;

public interface PurchaseRequisitionService {
    ResponseEntity<byte[]> generatePurchaseRequisition();
}
