package com.sazzad.erpReport.controller.indentItem;


import com.sazzad.erpReport.service.indentItem.impl.IndentItemReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settingdev/api/v1/")
public class IndentItemController {
    @Autowired
    private IndentItemReportServiceImpl indentItemReportServiceImpl;

    @GetMapping("/indent-item-report-pdf")
    public ResponseEntity<byte[]> downloadReport() {
        return indentItemReportServiceImpl.downloadReport();
    }
}