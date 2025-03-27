package com.sazzad.erpReport.service.receiveReport;

import org.springframework.http.ResponseEntity;

public interface ReceiveReportService {
    ResponseEntity<byte[]> generateReceiveReport();
}
