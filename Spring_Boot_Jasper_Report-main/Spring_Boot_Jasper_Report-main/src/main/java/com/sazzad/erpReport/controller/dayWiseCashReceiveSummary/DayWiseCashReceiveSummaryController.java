package com.sazzad.erpReport.controller.dayWiseCashReceiveSummary;


import com.sazzad.erpReport.service.dayWiseCashReceiveSummary.DayWiseCashReceiveSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class DayWiseCashReceiveSummaryController {
    @Autowired
    public DayWiseCashReceiveSummaryService dayWiseCashReceiveSummaryService;


    @GetMapping("/day-wise-cash-received-summary-report-pdf")
    public ResponseEntity<byte[]> getDailySellsCashReceivePdf() {
        return dayWiseCashReceiveSummaryService.generateDailySellsCashReceiveReport();
    }
}
