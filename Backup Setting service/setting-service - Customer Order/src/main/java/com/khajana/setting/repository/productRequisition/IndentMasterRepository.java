//package com.khajana.setting.repository.productRequisition;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class IndentMasterRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public IndentMasterRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Map<String, Object>> findIndentMasterById(Long id) {
//        String sql = "EXEC [dbo].[sp_get_indent_master_details] @id = ?";
//        return jdbcTemplate.queryForList(sql, id);
//    }
//}