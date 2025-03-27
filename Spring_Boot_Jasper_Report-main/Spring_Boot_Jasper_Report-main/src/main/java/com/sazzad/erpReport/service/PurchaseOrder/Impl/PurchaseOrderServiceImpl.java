package com.sazzad.erpReport.service.PurchaseOrder.Impl;



import com.sazzad.erpReport.dto.purchaseOrder.PurchaseOrderDto;
import com.sazzad.erpReport.service.PurchaseOrder.PurchaseOrderService;
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
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    public ResponseEntity<byte[]> generatePurchaseOrderReport() {
        try {
            List<PurchaseOrderDto> purchaseOrderData = Arrays.asList(
                    new PurchaseOrderDto(1,"Rice",25.0,"KG",100.00,2500.00,""),
                    new PurchaseOrderDto(2,"12 Waiter",12.0,"Person",100.00,2.0,"For the Program of Mr Jabed's Son's \n" +
                            "Meeriage Ceremony on 14 Apr 23")
            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/PurchaseOrderReport.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("purchaseOrderNo", "Purchase Order No:");
            parameters.put("poDate", " PO Date:");
            parameters.put("to", "To:");
            parameters.put("deliveryPoint", " Delivery Point:");
            parameters.put("deliveryDate", " Delivery Date:");
            parameters.put("payTerm", "Pay Tarm:");
            parameters.put("ChikleeNumber", "Report # C_B_04A");
            parameters.put("printDate", "Print: " + currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(purchaseOrderData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=indentSummaryReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

