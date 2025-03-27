package com.sazzad.erpReport.service.misMatchSalesReport;

import org.springframework.http.ResponseEntity;

public interface DailyMismatchSalesService {
    ResponseEntity<byte[]>generateDailyMismatchSalesReport();
}
