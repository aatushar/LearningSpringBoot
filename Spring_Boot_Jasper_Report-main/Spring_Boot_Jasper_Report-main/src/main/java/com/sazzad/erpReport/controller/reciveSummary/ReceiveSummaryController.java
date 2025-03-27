package com.sazzad.erpReport.controller.reciveSummary;



import com.sazzad.erpReport.service.reciveSummary.ReceiveSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class ReceiveSummaryController {
    @Autowired
    private ReceiveSummaryService receiveSummaryService;


    @GetMapping("/receive-summary-report-pdf")
    public ResponseEntity<byte[]> getReceiveSummary() {
        return receiveSummaryService.generateReceiveSummary();
    }
}
