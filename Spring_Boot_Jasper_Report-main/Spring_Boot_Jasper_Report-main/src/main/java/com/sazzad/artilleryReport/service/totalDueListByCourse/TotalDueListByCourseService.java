package com.sazzad.artilleryReport.service.totalDueListByCourse;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface TotalDueListByCourseService {
    void makePdfReport(HttpServletResponse httpServletResponse,  Long storeId, String Date,String Year );
}
