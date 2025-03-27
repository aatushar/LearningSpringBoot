package com.sazzad.artilleryReport.controller.dailySalesSummaryByItem;


import com.sazzad.artilleryReport.service.dailySalesSummaryByItem.DailySalesSummaryByItemService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.JMRuntimeException;
import java.io.IOException;

@RestController
@RequestMapping("/settingdev/api/v1/")
public class DailySalesSummaryByItemController {


    @Autowired
    private DailySalesSummaryByItemService dailySalesSummaryByItemService;

    @GetMapping("/daily-sales-summary-by-item-report-pdf")
    public void makePdfReport(
            HttpServletResponse response,
            @RequestParam(name = "transDate") String transDate,
            @RequestParam(name = "storeId") Long storeId
    ) throws IOException {
        dailySalesSummaryByItemService.makePdfReport(response, transDate, storeId);
    }
}
