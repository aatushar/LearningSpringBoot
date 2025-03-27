package com.sazzad.artilleryReport.controller.individualDueListByMonth;


import com.sazzad.artilleryReport.service.individualDueListByMonth.IndividualDueListByMonthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/settingdev/api/v1/")
public class IndividualDueListByMonthController {
    @Autowired
    private IndividualDueListByMonthService individualDueListByMonthService;


    @GetMapping("/individual-due-list-by-month-report-pdf")
    public void getReportPdf(
                             HttpServletResponse response,
                             @RequestParam(name = "StoreID") Long StoreID
    ) throws IOException {
        individualDueListByMonthService.makePdfReport(response, StoreID);
    }

}
