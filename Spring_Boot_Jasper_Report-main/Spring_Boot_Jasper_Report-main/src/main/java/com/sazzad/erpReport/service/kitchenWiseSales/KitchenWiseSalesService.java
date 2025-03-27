package com.sazzad.erpReport.service.kitchenWiseSales;

import org.springframework.http.ResponseEntity;

public interface KitchenWiseSalesService {
    ResponseEntity<byte[]> generateKitchenWiseSalesReport();
}
