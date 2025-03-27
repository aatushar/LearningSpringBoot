package com.sazzad.erpReport.service.mothWiseDailySelllsDetails;

import org.springframework.http.ResponseEntity;

public interface MonthWiseDailySellsDetailsService {
    ResponseEntity<byte[]> generateMonthWiseDailySellsReport();
}
