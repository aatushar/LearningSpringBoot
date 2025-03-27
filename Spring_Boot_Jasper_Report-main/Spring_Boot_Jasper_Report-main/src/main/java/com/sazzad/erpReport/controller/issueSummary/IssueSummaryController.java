package com.sazzad.erpReport.controller.issueSummary;


import com.sazzad.erpReport.service.issueSummary.IssueSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingdev/api/v1")
public class IssueSummaryController {
    @Autowired
    private IssueSummaryService issueSummaryService;


    @GetMapping("/issue-summary-report-pdf")
    public ResponseEntity<byte[]> getIssueSummary() {
        return issueSummaryService.generateIssueSummaryReport();
    }
}
