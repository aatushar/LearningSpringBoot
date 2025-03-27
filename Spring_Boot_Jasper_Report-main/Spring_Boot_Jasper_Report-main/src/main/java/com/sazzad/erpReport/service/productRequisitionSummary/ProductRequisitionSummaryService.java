package com.sazzad.erpReport.service.productRequisitionSummary;

import org.springframework.http.ResponseEntity;

public interface ProductRequisitionSummaryService {
    ResponseEntity<byte[]> generateRequisitionSummaryReport();
}