package com.sazzad.artilleryReport.service.individualDueListByMonth.impl;


import com.sazzad.artilleryReport.dto.individualDueListByMonth.IndividualDueListByMonthDto;
import com.sazzad.artilleryReport.service.individualDueListByMonth.IndividualDueListByMonthService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndividualDueListByMonthServiceImpl implements IndividualDueListByMonthService {
    private final JdbcTemplate jdbcTemplate;

    public IndividualDueListByMonthServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void makePdfReport(HttpServletResponse response, Long StoreID) {
        try {
            String sql = "GetIndividualDueListByMonth ?";
            List<IndividualDueListByMonthDto> dataList = jdbcTemplate.query(
                    sql, new BeanPropertyRowMapper<>(IndividualDueListByMonthDto.class), StoreID
            );
            if (dataList.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                response.getWriter().write("No data available for the given parametrs");
                return;
            }

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("reportName", "Individual Due List By Month");
            parameters.put("companyName", dataList.get(0).getCompanyName());
            parameters.put("storeName", dataList.get(0).getStoreName());
            parameters.put("artilleryLogo", "asset/artillery_logo.png");

            //Load the JasperReport template file
            InputStream inputStream = getClass().getResourceAsStream("/reports/artilleryReport/IndividualDueListByMonth.jrxml");
            if (inputStream == null) {
                throw new RuntimeException("Jasper report template not found");
            }
            //compile report
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

            //file report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);


            //set response content type
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename= IndividualDueListByMonth.pdf");

            //Export report to response t response output stream
            OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.flush();

        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void handleException(HttpServletResponse response, Exception e) {
        try {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write("Error generation report: " + e.getMessage());
        } catch (Exception ioException) {
            ioException.printStackTrace();

        }
    }
}



