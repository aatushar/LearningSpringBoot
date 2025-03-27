package com.sazzad.erpReport.service.issueReturn;

import org.springframework.http.ResponseEntity;

public interface IssueReturnService {
    ResponseEntity<byte[]> generateIssueReturnReport();
}
