package com.sazzad.erpReport.controller.receiveReport;


import com.sazzad.erpReport.service.receiveReport.ReceiveReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("settingdev/api/v1")
public class ReceiveReportController {

    @Autowired
    private ReceiveReportService receiveReportService;

    @GetMapping("receive-report-pdf")
    public ResponseEntity<byte[]> getReceiveReport() {
        return receiveReportService.generateReceiveReport();
    }
}
