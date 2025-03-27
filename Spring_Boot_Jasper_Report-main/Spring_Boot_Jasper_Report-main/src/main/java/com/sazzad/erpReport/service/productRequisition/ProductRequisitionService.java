package com.sazzad.erpReport.service.productRequisition;

import org.springframework.http.ResponseEntity;

public interface ProductRequisitionService {

    ResponseEntity<byte[]> generateProductRequisition();
}
