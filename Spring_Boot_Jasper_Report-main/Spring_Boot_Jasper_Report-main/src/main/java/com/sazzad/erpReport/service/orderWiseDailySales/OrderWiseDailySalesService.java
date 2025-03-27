package com.sazzad.erpReport.service.orderWiseDailySales;

import org.springframework.http.ResponseEntity;

public interface OrderWiseDailySalesService {
    ResponseEntity<byte[]> generateOrderWiseDailySalesReport();
}