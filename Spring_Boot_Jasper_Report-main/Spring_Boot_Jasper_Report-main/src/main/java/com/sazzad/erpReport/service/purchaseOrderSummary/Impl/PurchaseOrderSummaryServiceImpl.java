package com.sazzad.erpReport.service.purchaseOrderSummary.Impl;


import com.sazzad.erpReport.dto.purchaseOrderSummary.PurchaseOrderSummaryDto;
import com.sazzad.erpReport.service.purchaseOrderSummary.PurchaseOrderSummaryService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseOrderSummaryServiceImpl implements PurchaseOrderSummaryService {

    public ResponseEntity<byte[]> generatePurchaseOrderSummaryReport() {
        try {
            List<PurchaseOrderSummaryDto> purchaseOrderSummaryData= Arrays.asList(
                    new PurchaseOrderSummaryDto(1,55,"09-07-1999","Runner", "A55", "12-07-199", 900000.00, "close",""),
                    new PurchaseOrderSummaryDto(2,55,"09-07-1999","Runner", "A55", "12-07-199", 900000.00, "close",""),
                    new PurchaseOrderSummaryDto(3,55,"09-07-1999","Runner", "A55", "12-07-199", 900000.00, "close","")
            );
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/PurchaseOrderSummary.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Format the current date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("productMasterGroup", "Product Master Group : Dry Item");
            parameters.put("openPOtillDate", "Open PO till Date:");
            parameters.put("chikleeNumber", "Report # C_B_03C");
            parameters.put("printDate", "Print: " + currentDateTime);

            JRBeanCollectionDataSource dataSource = new  JRBeanCollectionDataSource(purchaseOrderSummaryData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);


            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition ", "inline; PurchaseOrderSummary.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}