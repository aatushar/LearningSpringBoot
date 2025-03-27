package com.sazzad.erpReport.service.productRequisition.Impl;


import com.sazzad.erpReport.dto.productRequisition.ProductRequisitionDto;
import com.sazzad.erpReport.service.productRequisition.ProductRequisitionService;
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
public class ProductRequisitionServiceImp implements ProductRequisitionService {

    @Override
    public ResponseEntity<byte[]> generateProductRequisition() {
        try {
            List<ProductRequisitionDto> productRequisitionList = Arrays.asList(
                    new ProductRequisitionDto(1,"2023-08-15", "Main Store", " Condence Milk", " Pcs",12.00, "good"),
                    new ProductRequisitionDto(2,"2023-08-15", "Main Store", " Egg- Chicken", " Pcs",32.00, "good")
            );
            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/ProductRequisition.jrxml");
            if (reportStream == null) {
                throw new JRException("Report template not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            // Parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("productRequisitionNo", " Product Requisition No: 000070");
            parameters.put("to", " To: Main Store");
            parameters.put("RequisitionDate", "  Requisition Date: 15-08-2023");
            parameters.put("chikleeNumber", "  Report # B_02A");
            parameters.put("PrintDate",currentDateTime );
            parameters.put("indNo", "000065");
            parameters.put("date", " 15-08-2023");
            parameters.put("masGrp", "Dry Items");
            parameters.put("fromStore", "Happy Zone");
            parameters.put("toStore", "Sub Store-Kitchen");


            // Data Source
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productRequisitionList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export Report to PDF
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            // Return as Response
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ProductRequisitionSummary.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
