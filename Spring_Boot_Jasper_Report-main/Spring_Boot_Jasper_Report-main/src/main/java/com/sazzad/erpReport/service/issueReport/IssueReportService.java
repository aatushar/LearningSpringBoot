package com.sazzad.erpReport.service.issueReport;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IssueReportService {
    ResponseEntity<byte[]> generateIssueReport();
}
