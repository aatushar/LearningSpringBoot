package com.khajana.setting.service.purchaseOrder;

import com.khajana.setting.dto.purchaseOrder.ApiResponse;
import com.khajana.setting.dto.purchaseOrder.PoGetOneChildDto;
import com.khajana.setting.dto.purchaseOrder.PoGetOneDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PoGetOneService {
    @Autowired
    private JdbcTemplate   jdbcTemplate;

    public ApiResponse findPoGetOneById(Long id) {
        try {
            String sql = "select * from po_get_one where po_id=?";

            PoGetOneDto poGetOneDto = jdbcTemplate.query(sql, new Object[]{id}, new ResultSetExtractor<PoGetOneDto>() {
                @Override
                public PoGetOneDto extractData(ResultSet rs) throws SQLException {
                    PoGetOneDto masterData = null;
                    List<PoGetOneChildDto> childDataList = new ArrayList<>();
                    while (rs.next()) {
                        if (masterData == null) {
                            masterData = new PoGetOneDto();
                            masterData.setId(rs.getLong("id"));
                        }

                        //process child data
                        PoGetOneChildDto childDto = new PoGetOneChildDto();
                        childDto.setId(rs.getLong("id"));

                        childDataList.add(childDto);

                    }
                    if (masterData != null) {
                        masterData.setChilds(childDataList);
                    }
                    return masterData;

                }
            });
            if (poGetOneDto == null){
               return new  ApiResponse(404, "no Records Found!", null, "");
            }
            return new ApiResponse(200, "", null, poGetOneDto);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResponse(500, e.getMessage(), null, "");
        }
    }

}
