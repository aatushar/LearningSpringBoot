package com.sazzad.erpReport.service.dayWiseCashReceiveSummary;

import org.springframework.http.ResponseEntity;

public interface DayWiseCashReceiveSummaryService {
    ResponseEntity<byte[]> generateDailySellsCashReceiveReport();
}
