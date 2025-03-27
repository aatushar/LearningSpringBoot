package com.sazzad.artilleryReport.service.individualDueListByOrderByItem.impl;


import com.sazzad.artilleryReport.dto.individualDueListByOrderByItem.IndividualDueListByOrderByItemDto;
import com.sazzad.artilleryReport.service.individualDueListByOrderByItem.IndividualDueListByOrderByItemService;
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
public class IndividualDueListByOrderByItemServiceImpl implements IndividualDueListByOrderByItemService {

    private final JdbcTemplate jdbcTemplate;

    public IndividualDueListByOrderByItemServiceImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void makePdfIndividualDueListByOrderByItem (HttpServletResponse response ,String transDate, Long storeId){
        try {
            String sql = "EXEC GetInvidualDueListByOrderByItem ?,? ";
            List<IndividualDueListByOrderByItemDto> dataList = jdbcTemplate.query(
                    sql, new BeanPropertyRowMapper<>(IndividualDueListByOrderByItemDto.class), transDate,storeId
                    );
            if (dataList.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                response.getWriter().write("No data available fro the given parameters.");
                return;

            }
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("reportName", "Individual Due List By Order By Item");
            parameters.put("companyName", dataList.get(0).getCompanyName());
            parameters.put("storeName", dataList.get(0).getStoreName());
            parameters.put("date", transDate);
            parameters.put("artilleryLogo", "asset/artillery_logo.png");

            InputStream inputStream = getClass().getResourceAsStream("/reports/artilleryReport/IndividualDueListByOrderByItem.jrxml");
            if (inputStream == null) {
                throw new RuntimeException("Jasper report template not found");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);


            response.setContentType("application/pdf");
            response.setHeader("content-Dispositon", "inline; filename= IndividualDueListByOrder.pdf");


            OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.flush();

        }catch (Exception e){
            handleException(response, e);

        }
    }
    private void handleException(HttpServletResponse response, Exception e) {
        try {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write("Error generation report: "+ e.getMessage());
        }catch (Exception ioException){
            ioException.printStackTrace();
        }

    }

}