package com.khajana.setting.service.productRequisition;

import com.google.gson.Gson;
import com.khajana.setting.dto.productRequisition.ApiResponse;
import com.khajana.setting.dto.productRequisition.CustomKeyValue;
import com.khajana.setting.dto.purchaseRequsition.DBCommonInsertAck;
import com.khajana.setting.dto.productRequisition.IndentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndentRequestService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ApiResponse insertData(IndentRequestDTO dto) {
        if (dto.getChild() == null || dto.getChild().isEmpty()) {
            List<CustomKeyValue> errorMessages = new ArrayList<>();
            errorMessages.add(new CustomKeyValue("child", "Child items can't be empty"));
            return new ApiResponse(400, "Bad Request", errorMessages, "");
        }
        try {
            Gson gson = new Gson();
            String jsonStr = gson.toJson(dto);

            String sql = "EXEC [dbo].[SP_INSERT_4B_INDENT_MASTER] ?, ?";
            List<DBCommonInsertAck> commonInsertAcks = jdbcTemplate.query(
                    sql,
                    new Object[]{jsonStr, 1},
                    BeanPropertyRowMapper.newInstance(DBCommonInsertAck.class)
            );

            DBCommonInsertAck dbresult = commonInsertAcks.isEmpty() ? null : commonInsertAcks.get(0);

            if (dbresult != null && dbresult.getStatus() == 1) {
                return new ApiResponse(200, dbresult.getMessage(), null, dbresult.getResult());
            } else {
                return new ApiResponse(400, dbresult != null ? dbresult.getMessage() : "Insert failed", null, dbresult != null ? dbresult.getResult() : null);
            }
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), null, "");
        }
    }
}
