package com.sazzad.artilleryReport.service.individualDueListByMonth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IndividualDueListByMonthService {
   void makePdfReport(HttpServletResponse httpServletResponse, Long StoreId);
}
