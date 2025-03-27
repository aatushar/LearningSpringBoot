package com.sazzad.artilleryReport.controller.dailySalesSummary;


import com.sazzad.artilleryReport.service.dailySalesSummary.DailySalesSummaryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/settingdev/api/v1/")
public class DailySalesSummaryController {

    @Autowired
    private DailySalesSummaryService dailySalesSummaryService;

    @GetMapping("/daily-sales-summary-report-pdf")
    public void makePdfReport(
            HttpServletResponse response,
            @RequestParam(name = "StoreID") Long StoreID
    ) throws IOException {
        dailySalesSummaryService.makePdfReport(response, StoreID);
    }
}
