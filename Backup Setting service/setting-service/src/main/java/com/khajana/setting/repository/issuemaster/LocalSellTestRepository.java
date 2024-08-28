package com.khajana.setting.repository.issuemaster;

import com.khajana.setting.entity.issuemaster.LocalSellTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocalSellTestRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<LocalSellTest> executeStoredProcedur() {
        return jdbcTemplate.query("EXEC [dbo].[GetAllSellIndexData] @prodTypeId = 1", (rs, rowNum) -> {
            LocalSellTest houseKeeping = new LocalSellTest();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setIssueDeliveryNo(rs.getString("issueNo"));
            houseKeeping.setIssueDeliveryNoBn(rs.getString("issueNoBn"));
            houseKeeping.setIssueDate(rs.getDate("issueDate"));
            houseKeeping.setChallanNumber(rs.getString("challanNo"));
            houseKeeping.setChallanNumberBn(rs.getString("challanNoBn"));
            houseKeeping.setCahallanDate(rs.getDate("challanDate"));
            houseKeeping.setCustomerCatId(rs.getInt("custId"));
            houseKeeping.setCustomerName(rs.getString("customerName"));
            houseKeeping.setCustomerNameBn(rs.getString("customerNameBn"));
            houseKeeping.setCustomerBinNumber(rs.getString("custBin"));
            houseKeeping.setCustomerBinNumberBn(rs.getString("custBinBn"));
            houseKeeping.setRemarks(rs.getString("remark"));
            houseKeeping.setRemarksBn(rs.getString("remarkBn"));
            return houseKeeping;
        });
    }
}