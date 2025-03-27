package com.sazzad.erpReport.service.itmeWiseDailySales;

import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;

public interface ItemWiseDailySalesService {
    ResponseEntity<byte[]> generateDailySalesReport() ;
}
