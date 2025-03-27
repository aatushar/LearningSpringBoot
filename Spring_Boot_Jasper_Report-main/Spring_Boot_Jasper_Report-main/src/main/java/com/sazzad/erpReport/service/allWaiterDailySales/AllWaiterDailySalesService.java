package com.sazzad.erpReport.service.allWaiterDailySales;

import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;

public interface AllWaiterDailySalesService {
   ResponseEntity <byte[]> generateAllWaiterDailySalesReport() ;
}