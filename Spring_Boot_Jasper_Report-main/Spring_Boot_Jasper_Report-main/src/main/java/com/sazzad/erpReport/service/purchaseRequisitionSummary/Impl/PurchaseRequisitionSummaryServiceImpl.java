package com.sazzad.erpReport.service.purchaseRequisitionSummary.Impl;

import com.sazzad.erpReport.dto.purchaseRequisitionSummary.PurchaseRequisitionSummaryDto;
import com.sazzad.erpReport.service.purchaseRequisitionSummary.PurchaseRequisitionSummaryService;
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
public class PurchaseRequisitionSummaryServiceImpl implements PurchaseRequisitionSummaryService {

    public ResponseEntity<byte[]> generatePurchaseRequisitionSummaryReport() {

        try {
            List<PurchaseRequisitionSummaryDto> requisitionData= Arrays.asList(
                    new PurchaseRequisitionSummaryDto(1,"000123","23-10-2023", "Zit", "Raw Materials","Open","Zit",""),
                    new PurchaseRequisitionSummaryDto(2,"000116","23-10-2023", "Zit", "Raw Materials","Closed", "Zit",""),
                    new PurchaseRequisitionSummaryDto(3,"000117","23-10-2023", "Zit", "Raw Materials","Open","Zit",""),
                    new PurchaseRequisitionSummaryDto(4,"000118","23-10-2023", "Zit", "Raw Materials","Open","Zit",""),
                    new PurchaseRequisitionSummaryDto(5,"000119","23-10-2023", "Zit", "Raw Materials","Open","Zit",""),
                    new PurchaseRequisitionSummaryDto(6,"000120","23-10-2023", "Zit", "Raw Materials","Open","Zit",""),
                    new PurchaseRequisitionSummaryDto(7,"000121","23-10-2023", "Zit", "Raw Materials","Open","Zit","")

            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/PurchaseRequisitionSummaryReport.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);



            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fromDate", "From Date :01-01-2023");
            parameters.put("toDate", "To Date: 01-01-2023");
            parameters.put("chikleeNumber", "Report # C_B_03B");
            parameters.put("print", currentDateTime);

            JRBeanCollectionDataSource dataSource = new  JRBeanCollectionDataSource(requisitionData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);


            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition ", "inline; filename=PurchaseRequisitionSummaryReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}