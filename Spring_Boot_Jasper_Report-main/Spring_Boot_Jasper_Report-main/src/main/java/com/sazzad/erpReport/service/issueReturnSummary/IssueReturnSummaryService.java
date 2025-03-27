package com.sazzad.erpReport.service.issueReturnSummary;

import org.springframework.http.ResponseEntity;

public interface IssueReturnSummaryService {
    ResponseEntity<byte[]>generateIssueReturnSummaryReport();
}
