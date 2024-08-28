package com.khajana.setting.service.purchaseOrder;

import com.google.gson.Gson;
import com.khajana.setting.dto.purchaseOrder.ApiResponse;
import com.khajana.setting.dto.purchaseOrder.CustomKeyValue;
import com.khajana.setting.dto.purchaseOrder.DBCommonInsertAck;
import com.khajana.setting.dto.purchaseOrder.PoInsertDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PoInsertService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ApiResponse insertData(PoInsertDto dto){
        if (dto.getChild()==null || dto.getChild().isEmpty()){
            List<CustomKeyValue> errorMessage = new ArrayList<>();
            errorMessage.add(new CustomKeyValue("child","child items cannot be empty"));
            return new ApiResponse(400, "Bad Request", errorMessage, "");

        }
        try {
            Gson gson = new Gson();
            String jsonData = gson.toJson(dto);

            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("insert_po")
                    .declareParameters(
                            new SqlParameter("jsonData", Types.VARCHAR),
                            new SqlParameter("flag", Types.INTEGER),
                            new SqlParameter("CreatedBy", Types.BIGINT),
                            new SqlParameter("result", Types.REF_CURSOR)
                    )
                    .returningResultSet("result", BeanPropertyRowMapper.newInstance(DBCommonInsertAck.class));
            Map<String, Object> result = jdbcCall.execute(Map.of(
                    "jsonData", jsonData,
                    "flag", 1,
                    "CreatedBy", dto.getChild() //here should be dto.getCreatBy()
            ));
            List<DBCommonInsertAck> commonInsertAcks = (List<DBCommonInsertAck>) result.get("result");

            DBCommonInsertAck dbResult = commonInsertAcks.isEmpty() ? null : commonInsertAcks.get(0);
            if (dbResult != null && dbResult.getStatus() == 1) {
                return new ApiResponse(200, dbResult.getMessage(), null, dbResult.getResult());

            }else {return new ApiResponse(400, dbResult !=null ? dbResult.getMessage():"Insert failed",null, dbResult!=null?dbResult.getResult():null);
        }
            }catch (Exception e){
            return new ApiResponse(500, e.getMessage(), null,"");
        }
    }


}
