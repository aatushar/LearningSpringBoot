package com.sazzad.erpReport.service.itemOpeningStockReport;

import org.springframework.http.ResponseEntity;

public interface ItemOpeningStockService {

    ResponseEntity<byte[]> generateItemOpeningStockReport();
}