package com.sazzad.erpReport.service.iocDetaisApproval.Impl;


import com.sazzad.erpReport.dto.iocDetailsApproval.IocRawMaterialsDto;
import com.sazzad.erpReport.service.iocDetaisApproval.IocDetailsApprovalService;
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
public class IocDetailsApprovalServiceImpl implements IocDetailsApprovalService {

    public ResponseEntity<byte[]> generateIocDetailsApprovalReport() {

        try {
            List<IocRawMaterialsDto> rawMaterialsData= Arrays.asList(
                    new IocRawMaterialsDto(1,"Rice",2, "KG", 185.00,370.00,"")
            );

            InputStream reportStream = getClass().getResourceAsStream("/reports/chikleeErpreports/IocDetailsApproval.jrxml");

            if (reportStream == null) {
                throw new JRException("Report not found");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Format the current date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String currentDateTime = LocalDateTime.now().format(formatter);


            // Load the logo
//            BufferedImage logoImage = ImageIO.read(new ClassPathResource("asset/chiklee_logo.png").getInputStream());




            Map<String, Object> parameters = new HashMap<>();
            parameters.put("chikleeLogo","asset/chiklee_logo.png");
            parameters.put("companyName", "Chiklee Water Park");
            parameters.put("effectiveDate", "Effective Date");
            parameters.put("itemName", "Raw Materials");
            parameters.put("chikleeNumber", "Report # C_C_01A");
            parameters.put("printDate", "Print" + currentDateTime);
            parameters.put("submittedBy", "" );
            parameters.put("recommededBy", "" );
            parameters.put("approvedBy", "" );
            parameters.put("IocDetailsApproval",rawMaterialsData );

            JRBeanCollectionDataSource dataSource = new  JRBeanCollectionDataSource(rawMaterialsData);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);

            byte[] pdfData = JasperExportManager.exportReportToPdf(jasperPrint);


            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition ", "inline; filename=IocDetailsApproval.pdf");

            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfData);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}