package com.sazzad.erpReport.service.storeWiseItemList.impl;


import com.sazzad.erpReport.dto.storeWiseItemList.StoreWiseItemListDto;
import com.sazzad.erpReport.service.storeWiseItemList.StoreWiseItemListService;
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
public class StoreWiseItemListServiceImpl implements StoreWiseItemListService {
    public ResponseEntity<byte[]> generateStoreWiseItemListReport() {
        try{
            List<StoreWiseItemListDto> storeWiseItemListData = Arrays.asList(
                    new StoreWiseItemListDto(1, "554455515455",  17743, " Breakfast", " Alu Vaji", " আলু ভাজি", 30.00,""),
                    new StoreWiseItemListDto(2, "215454556456454",  17743, " Breakfast", " Alu Vaji", "  আলু ভাজি", 30.00,"")

            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/StoreWiseItemListReport.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("chikleelogo", "asseet/chiklee_logo.png");
            parameters.put("date", "11/01/1999");
            parameters.put("submitedBy", "");
            parameters.put("recommededBy","");
            parameters.put("approvedBy","");
            parameters.put("chikleeNumber", "Report Chiklee#Z_01");
            parameters.put("printDate",currentDateTime);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(storeWiseItemListData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=StoreWiseItemListReport.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        } catch(
                Exception e)

        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

