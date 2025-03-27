package com.sazzad.erpReport.controller.waiterWiseDailySales;


import com.sazzad.erpReport.service.waiterWiseDailySales.WaiterWiseDailySalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingdev/api/v1")
public class WaiterWiseDailySalesController {

    @Autowired
    private WaiterWiseDailySalesService waiterWiseDailySalesService;

    @GetMapping("/waiter-wise-daily-sales-report-pdf")
    public ResponseEntity<byte[]> getWaiterWiseDailySalesReport() {
        return waiterWiseDailySalesService.generateWaiterWiseDailySalesReport();
    }
}