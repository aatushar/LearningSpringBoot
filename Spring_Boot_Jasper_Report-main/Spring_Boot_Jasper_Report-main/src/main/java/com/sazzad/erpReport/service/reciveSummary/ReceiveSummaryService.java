package com.sazzad.erpReport.service.reciveSummary;

import org.springframework.http.ResponseEntity;

public interface ReceiveSummaryService {
    ResponseEntity<byte[]> generateReceiveSummary();
}
