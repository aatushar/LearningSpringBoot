package com.sazzad.erpReport.service.indentSummary;

import org.springframework.http.ResponseEntity;

public interface IndentSummaryService {
    ResponseEntity<byte[]> generateIndentSummaryReport();
}
