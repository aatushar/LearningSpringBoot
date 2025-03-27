package com.sazzad.erpReport.service.dailySalesVsCashReceive;

import org.springframework.http.ResponseEntity;

public interface DailySellsVsCashReceiveService {
    ResponseEntity<byte[]> generateDailySellsVsCashReceiveReport();
}
