package com.sazzad.erpReport.controller.dailySalesVsCashReceive;



import com.sazzad.erpReport.service.dailySalesVsCashReceive.DailySellsVsCashReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class DailySalesVsCashReceiveController {

    @Autowired
    private DailySellsVsCashReceiveService dailySellsVsCashReceiveService;

    @GetMapping("/daily-sales-vs-cash-receive-report-pdf")
    public ResponseEntity<byte[]> getDailySalesVsCashReceiveReportPdf() {
        return dailySellsVsCashReceiveService.generateDailySellsVsCashReceiveReport();
    }
}
