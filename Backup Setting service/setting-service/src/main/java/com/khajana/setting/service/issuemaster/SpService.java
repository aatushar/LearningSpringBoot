package com.khajana.setting.service.issuemaster;

import com.khajana.setting.repository.issuemaster.SpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SpService {
    @Autowired
    SpRepo spRepo;
    @Autowired
    JdbcTemplate jdbcTemplate;

    //    public List<SpEntity> getAllSellIndexData(int prodTypeId) {
//        return spRepo.getAllSellIndexData(prodTypeId);
//    }
    public List<Map<String, Object>> getAllSellIndexData(int prodTypeId) {
        String sql = "EXEC [dbo].[GetAllSellIndexData] @prodTypeId = ?";
        return jdbcTemplate.queryForList(sql, prodTypeId);
    }
//public List<SpEntity> getUserData() {
//    return spRepo.getUserData();
//}

//public List<SpoHouseKeeping> getStores() {
//    return spRepo.getAllSellIndexData();
//}
}
