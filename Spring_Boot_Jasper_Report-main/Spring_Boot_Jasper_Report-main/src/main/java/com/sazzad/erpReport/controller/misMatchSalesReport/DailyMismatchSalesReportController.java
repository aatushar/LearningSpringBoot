package com.sazzad.erpReport.controller.misMatchSalesReport;


import com.sazzad.erpReport.service.misMatchSalesReport.DailyMismatchSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class DailyMismatchSalesReportController {

    @Autowired
    private DailyMismatchSalesService dailyMismatchSalesService;

    @GetMapping("/mismatch-Sales-report-pdf")
    public ResponseEntity<byte[]> getDailyMismatchSalesReport() {
        return dailyMismatchSalesService.generateDailyMismatchSalesReport();
    }
}
