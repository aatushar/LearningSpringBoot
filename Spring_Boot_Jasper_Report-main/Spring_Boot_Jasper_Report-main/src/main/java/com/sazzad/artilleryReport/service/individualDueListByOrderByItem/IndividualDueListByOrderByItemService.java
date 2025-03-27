package com.sazzad.artilleryReport.service.individualDueListByOrderByItem;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IndividualDueListByOrderByItemService {
    void makePdfIndividualDueListByOrderByItem (HttpServletResponse httpServletResponse ,String transDate, Long storeId);
}
