package com.khajana.setting.service.purchaseRequsiton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.khajana.setting.dto.productRequisition.ApiResponse;
import com.khajana.setting.dto.productRequisition.CustomKeyValue;
import com.khajana.setting.dto.purchaseRequsition.DBCommonInsertAck;
import com.khajana.setting.dto.purchaseRequsition.DemoInsertPkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DemoInsertService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ApiResponse insertData(DemoInsertPkDto dto) {
        if (dto.getChild() == null || dto.getChild().isEmpty()) {
            List<CustomKeyValue> errorMessages = new ArrayList<>();
            errorMessages.add(new CustomKeyValue("child", "Child items can't be empty"));
            return new ApiResponse(400, "Bad Request", errorMessages, "");
        }

        try {
            // Customize the date format
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
            String jsonData = gson.toJson(dto); // Change the variable name to jsonData

            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("SP_INSERT_4D_PURCHASE_REQUISITION_MASTER")
                    .declareParameters(
                            new SqlParameter("jsonData", Types.VARCHAR), // Update parameter name
                            new SqlParameter("flag", Types.INTEGER),
                            new SqlParameter("CreatedBy", Types.BIGINT), // Include the CreatedBy parameter
                            new SqlOutParameter("result", Types.REF_CURSOR)
                    )
                    .returningResultSet("result", BeanPropertyRowMapper.newInstance(DBCommonInsertAck.class));

            Map<String, Object> result = jdbcCall.execute(Map.of(
                    "jsonData", jsonData, // Update the map key to jsonData
                    "flag", 1,
                    "CreatedBy", dto.getCreatedBy() // Pass the CreatedBy parameter
            ));

            List<DBCommonInsertAck> commonInsertAcks = (List<DBCommonInsertAck>) result.get("result");

            DBCommonInsertAck dbResult = commonInsertAcks.isEmpty() ? null : commonInsertAcks.get(0);

            if (dbResult != null && dbResult.getStatus() == 1) {
                return new ApiResponse(200, dbResult.getMessage(), null, dbResult.getResult());
            } else {
                return new ApiResponse(400, dbResult != null ? dbResult.getMessage() : "Insert failed", null, dbResult != null ? dbResult.getResult() : null);
            }
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), null, "");
        }
    }
}