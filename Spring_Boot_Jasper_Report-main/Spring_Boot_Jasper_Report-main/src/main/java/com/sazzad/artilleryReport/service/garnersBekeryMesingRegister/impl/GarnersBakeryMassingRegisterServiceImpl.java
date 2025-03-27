package com.sazzad.artilleryReport.service.garnersBekeryMesingRegister.impl;
import com.sazzad.artilleryReport.dto.garnersBekeryMesingRegister.GarnersBakeryMassingRegisterDto;
import com.sazzad.artilleryReport.service.garnersBekeryMesingRegister.GarnersBakeryMassingRegisterService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GarnersBakeryMassingRegisterServiceImpl implements GarnersBakeryMassingRegisterService {
    private final JdbcTemplate jdbcTemplate;

    public GarnersBakeryMassingRegisterServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void makePdfReport(HttpServletResponse response, Long StoreID) {
        try {
            // Properly formatted SQL procedure call
            String sql = "EXEC mesingRegister ?";
            List<GarnersBakeryMassingRegisterDto> dataList = jdbcTemplate.query(
                    sql, new BeanPropertyRowMapper<>(GarnersBakeryMassingRegisterDto.class), StoreID
            );

            if (dataList.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                response.getWriter().write("No data available for the given parameters.");
                return;
            }

            // Parameters for the report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("reportName", "মিসিং রেজিস্টার( দ্রব্যের নাম (FG))");
            parameters.put("address", "আর্টিলারি সেন্টারে এন্ড স্কুল, হালিশহর ক্যান্টনমেন্ট, চট্টগ্রাম");
            parameters.put("date","15-08-2023");
            parameters.put("seniorCraftsman","");
            parameters.put("bakeryNco", "");
            parameters.put("bakeryGco", "");
            parameters.put("bakeryOic", "");
            parameters.put("fg2", "460");

            // Load the JasperReports template file
            InputStream inputStream = getClass().getResourceAsStream("/reports/artilleryReport/GarnersBakeryMassingReport.jrxml");
            if (inputStream == null) {
                throw new RuntimeException("Jasper report template not found.");
            }

            // Compile report
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

            // Fill report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Set response content type
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=GarnersBakeryMassingReport.pdf");

            // Export report to response output stream
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
            response.getWriter().write("Error generating report: " + e.getMessage());
        } catch (Exception ioException) {
            ioException.printStackTrace();
        }
    }
}
