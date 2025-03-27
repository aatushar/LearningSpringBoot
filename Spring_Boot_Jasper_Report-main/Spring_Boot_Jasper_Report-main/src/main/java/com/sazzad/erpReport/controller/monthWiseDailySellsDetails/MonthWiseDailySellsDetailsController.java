package com.sazzad.erpReport.controller.monthWiseDailySellsDetails;


import com.sazzad.erpReport.service.mothWiseDailySelllsDetails.MonthWiseDailySellsDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class MonthWiseDailySellsDetailsController {

    @Autowired
    private MonthWiseDailySellsDetailsService monthWiseDailySellsDetailsService;


    @GetMapping("/month-wise-daily-sells-details-service-report-pdf")
    public ResponseEntity<byte[]> getMonthWiseDailySellsDetails() {
        return monthWiseDailySellsDetailsService.generateMonthWiseDailySellsReport();
    }
}
