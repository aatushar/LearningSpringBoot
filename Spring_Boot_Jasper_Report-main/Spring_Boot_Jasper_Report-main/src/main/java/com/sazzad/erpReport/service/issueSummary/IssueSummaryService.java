package com.sazzad.erpReport.service.issueSummary;

import org.springframework.http.ResponseEntity;

public interface IssueSummaryService {
    ResponseEntity<byte[]> generateIssueSummaryReport();
}
