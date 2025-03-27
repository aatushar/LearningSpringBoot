package com.sazzad.erpReport.service.itemStockSummary;

import org.springframework.http.ResponseEntity;

public interface ItemStockSummaryService {
    ResponseEntity<byte[]> generateItemStockSummaryReport();
}
