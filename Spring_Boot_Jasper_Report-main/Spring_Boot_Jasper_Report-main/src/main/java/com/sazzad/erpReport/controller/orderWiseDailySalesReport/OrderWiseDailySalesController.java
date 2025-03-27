package com.sazzad.erpReport.controller.orderWiseDailySalesReport;


import com.sazzad.erpReport.service.orderWiseDailySales.OrderWiseDailySalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingdev/api/v1")
public class OrderWiseDailySalesController {
    @Autowired
    private OrderWiseDailySalesService orderWiseDailySalesService;

    @GetMapping("/order-wise-daily-sales-report-pdf")
    public ResponseEntity<byte[]> getOrderWiseDailySalesReport() {
        return orderWiseDailySalesService.generateOrderWiseDailySalesReport();
    }
}