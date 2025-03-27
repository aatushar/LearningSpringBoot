package com.sazzad.erpReport.service.indentItem;

import org.springframework.http.ResponseEntity;

public interface IndentItemReportService {
    ResponseEntity<byte[]> downloadReport();
}
