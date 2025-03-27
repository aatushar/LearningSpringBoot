package com.sazzad.artilleryReport.service.storeIdDropdown;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StoreIdDropDownService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getStoreDropdown() {
        String sql = "EXEC storeIdDropdown";
        return jdbcTemplate.queryForList(sql);
    }
}