package com.sazzad.erpReport.service.dateWiseSupplierPayment;

import org.springframework.http.ResponseEntity;

public interface DateWiseSupplierPaymentService {
    ResponseEntity<byte[]> generateDateWiseSupplierPaymentReport();
}
