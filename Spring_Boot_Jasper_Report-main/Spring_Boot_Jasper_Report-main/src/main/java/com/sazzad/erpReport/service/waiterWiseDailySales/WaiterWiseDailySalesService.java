package com.sazzad.erpReport.service.waiterWiseDailySales;

import org.springframework.http.ResponseEntity;

public interface WaiterWiseDailySalesService {
    ResponseEntity<byte[]> generateWaiterWiseDailySalesReport();
}