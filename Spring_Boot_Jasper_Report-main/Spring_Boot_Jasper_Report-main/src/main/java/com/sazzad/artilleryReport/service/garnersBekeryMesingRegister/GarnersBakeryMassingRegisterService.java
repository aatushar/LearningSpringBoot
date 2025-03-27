package com.sazzad.artilleryReport.service.garnersBekeryMesingRegister;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface GarnersBakeryMassingRegisterService {
    void  makePdfReport(HttpServletResponse httpServletResponse, Long StoreID );
}
