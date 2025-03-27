package com.sazzad.erpReport.controller.dailyCashReceiveBySellsPoint;


import com.sazzad.erpReport.service.dailyCashReceiveBySellsPoint.DailyCashReceiveBySellsPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class DailyCashReceivedBySellsController {
    @Autowired
    public DailyCashReceiveBySellsPointService dailyCashReceiveBySellsPointService;


    @GetMapping("/daily-sells-cash-receive-sells-point-report-pdf")
    public ResponseEntity<byte[]> getDailyCashReceiveReportPdf() {
        return dailyCashReceiveBySellsPointService.generateDailyCashReceiveBySellsPointReport();
    }
}
