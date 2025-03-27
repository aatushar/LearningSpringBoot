package com.sazzad.erpReport.controller.KitchenWiseSalesContoller;


import com.sazzad.erpReport.service.kitchenWiseSales.KitchenWiseSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingdev/api/v1")
public class KitchenWiseSalesController {

    @Autowired
    private KitchenWiseSalesService kitchenWiseSalesService;

    @GetMapping("/kitchen-wise-sales-report-pdf")
    public ResponseEntity<byte[]> getKitchenWiseSalesReport() {
        return kitchenWiseSalesService.generateKitchenWiseSalesReport();
    }
}