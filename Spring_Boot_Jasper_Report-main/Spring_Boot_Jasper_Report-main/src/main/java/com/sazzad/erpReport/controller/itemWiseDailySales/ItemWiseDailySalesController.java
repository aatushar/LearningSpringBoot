package com.sazzad.erpReport.controller.itemWiseDailySales;


import com.sazzad.erpReport.service.itmeWiseDailySales.ItemWiseDailySalesService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingdev/api/v1")
public class ItemWiseDailySalesController {
    @Autowired
    private ItemWiseDailySalesService itemWiseDailySalesService;

    @GetMapping("/item-wise-daily-sales-report-pdf")
    public ResponseEntity<byte[]> getDailySalesReport() {
        return itemWiseDailySalesService.generateDailySalesReport();
    }
}
