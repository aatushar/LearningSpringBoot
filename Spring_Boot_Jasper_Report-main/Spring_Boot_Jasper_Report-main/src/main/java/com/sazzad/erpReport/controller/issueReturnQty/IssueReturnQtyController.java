package com.sazzad.erpReport.controller.issueReturnQty;


import com.sazzad.erpReport.service.issueReturnQty.IssueReturnQtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class IssueReturnQtyController {

    @Autowired
    private IssueReturnQtyService issueReturnQtyService;

    @GetMapping("/issue-return-qty-report-pdf")
    public ResponseEntity<byte[]> getIssueReturnQtyReportPdf() {
        return issueReturnQtyService.generateIssueReturnQtyReport();
    }

}
