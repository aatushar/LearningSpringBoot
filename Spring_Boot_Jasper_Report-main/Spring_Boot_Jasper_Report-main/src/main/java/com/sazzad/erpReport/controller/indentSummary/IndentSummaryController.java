package com.sazzad.erpReport.controller.indentSummary;


import com.sazzad.erpReport.service.indentSummary.IndentSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class IndentSummaryController {

    @Autowired
    private IndentSummaryService indentSummaryService;


    @GetMapping("/indent-summary-report-pdf")
    public ResponseEntity<byte[]> getIndentSummary() {
        return indentSummaryService.generateIndentSummaryReport();
    }
}
