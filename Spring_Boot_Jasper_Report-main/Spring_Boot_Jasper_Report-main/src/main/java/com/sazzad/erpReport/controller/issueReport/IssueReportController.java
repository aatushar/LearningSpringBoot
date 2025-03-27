package com.sazzad.erpReport.controller.issueReport;


import com.sazzad.erpReport.service.issueReport.IssueReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class IssueReportController {

    @Autowired
    private IssueReportService issueReportService;

    @GetMapping("/issue-report-pdf")
    public ResponseEntity<byte[]> getIssueReport() {
        return issueReportService.generateIssueReport();
    }
}
