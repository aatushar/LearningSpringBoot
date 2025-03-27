package com.sazzad.erpReport.service.dailyCashReceiveBySellsPoint;

import org.springframework.http.ResponseEntity;

public interface DailyCashReceiveBySellsPointService {
    ResponseEntity<byte[]> generateDailyCashReceiveBySellsPointReport();
}
