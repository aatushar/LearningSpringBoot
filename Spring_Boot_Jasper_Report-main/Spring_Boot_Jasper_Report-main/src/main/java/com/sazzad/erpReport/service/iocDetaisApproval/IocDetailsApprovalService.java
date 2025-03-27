package com.sazzad.erpReport.service.iocDetaisApproval;

import org.springframework.http.ResponseEntity;

public interface IocDetailsApprovalService {
    ResponseEntity<byte[]> generateIocDetailsApprovalReport();
}
