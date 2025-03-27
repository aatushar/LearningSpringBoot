package com.sazzad.erpReport.controller.issueReturn;


import com.sazzad.erpReport.service.issueReturn.IssueReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingdev/api/v1")
public class IssueReturnController {
    @Autowired
    private IssueReturnService issueReturnService;


    @GetMapping("/issue-return-report-pdf")
    public ResponseEntity<byte[]> getIssueReturn() {
        return issueReturnService.generateIssueReturnReport();
    }
}
