package com.sazzad.erpReport.controller.allWaiterDailySales;


import com.sazzad.erpReport.service.allWaiterDailySales.AllWaiterDailySalesService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingdev/api/v1")
public class AllWaiterDailySalesController {

    @Autowired
    private AllWaiterDailySalesService allWaiterDailySalesService;


    @GetMapping("/all-waiter-daily-sales-report-pdf")
    public ResponseEntity<byte[]> generateAllWaiterDailySalesReport() {
        return allWaiterDailySalesService.generateAllWaiterDailySalesReport();
    }
}
