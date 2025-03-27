package com.sazzad.artilleryReport.service.individualDueListByOrder;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IndividualDueListByOrderService {
    void makePdfIndividualDueListByOrder (HttpServletResponse httpServletResponse , String transDate, Long storeId);
}
