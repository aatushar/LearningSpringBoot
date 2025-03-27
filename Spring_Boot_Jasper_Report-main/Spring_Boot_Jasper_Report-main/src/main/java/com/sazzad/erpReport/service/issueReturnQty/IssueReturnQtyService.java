package com.sazzad.erpReport.service.issueReturnQty;

import org.springframework.http.ResponseEntity;

public interface IssueReturnQtyService {
    ResponseEntity<byte[]> generateIssueReturnQtyReport();
}
