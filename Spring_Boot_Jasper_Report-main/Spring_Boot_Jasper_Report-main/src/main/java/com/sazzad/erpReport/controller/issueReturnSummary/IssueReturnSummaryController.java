package com.sazzad.erpReport.controller.issueReturnSummary;


import com.sazzad.erpReport.service.issueReturnSummary.IssueReturnSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("settingdev/api/v1")
public class IssueReturnSummaryController {

    @Autowired
    private IssueReturnSummaryService issueReturnSummaryService;

    @GetMapping("/issue-report-summary-report-pdf")
    public ResponseEntity<byte[]> getIssueReportSummaryReportPdf() {
        return issueReturnSummaryService.generateIssueReturnSummaryReport();
    }
}
