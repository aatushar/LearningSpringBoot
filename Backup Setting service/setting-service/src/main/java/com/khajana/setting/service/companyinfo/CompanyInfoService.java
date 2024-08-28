package com.khajana.setting.service.companyinfo;

import com.khajana.setting.dto.CustomResponse;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.utils.DateUtilForSp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CompanyInfoService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserCredentialRepository userCredentials;

    private String getUserNameById(Long userId) {

        User createdByUser = userCredentials.findById(userId).orElse(null);
        return createdByUser != null ? createdByUser.getName() : null;
    }

    public CustomResponse getAllCompanyInfoIndexById(int prodTypeId) {


        CustomResponse response = new CustomResponse();
        String sql = "EXEC [dbo].[sp_get_3A_company_info_list_by_id] @companyId = ?";
        List<Map<String, Object>> content = jdbcTemplate.queryForList(sql, prodTypeId);

        // todays trying start
        for (Map<String, Object> entry : content) {
            // Update "createdBy" and "updatedBy" fields with user names
            entry.put("createdBy", getUserNameById((Long) entry.get("createdBy")));
            entry.put("updatedBy", getUserNameById((Long) entry.get("updatedBy")));

            entry.put("createdAt", DateUtilForSp.formatToCustomString((Date) entry.get("createdAt")));
            entry.put("updatedAt", DateUtilForSp.formatToCustomString((Date) entry.get("updatedAt")));
        }
//todays end
        response.setCode(200);
        response.setMessage("Paginated results");
        CustomResponse.Result result = new CustomResponse.Result();
        result.setContent(content);
        result.setTotalElements(content.size());
        result.setTotalPages(1); // Assuming all data is fetched in one page
        result.setPage(0);
        result.setSize(content.size());
        result.setSort(Collections.singletonList("id,ASC")); // Provide appropriate sorting information if needed
        response.setResult(result);
        return response;
    }


    public CustomResponse getAllCompanyInfoIndexList(String sortField, String sortOrder) {
        CustomResponse response = new CustomResponse();

        String sql = "EXEC sp_get_3A_company_info_list @SortField = ?, @SortOrder = ?";

        List<Map<String, Object>> content = jdbcTemplate.queryForList(sql, sortField, sortOrder);
// todays trying start
        for (Map<String, Object> entry : content) {
            // Update "createdBy" and "updatedBy" fields with user names
            entry.put("createdBy", getUserNameById((Long) entry.get("createdBy")));
            entry.put("updatedBy", getUserNameById((Long) entry.get("updatedBy")));
            entry.put("createdAt", DateUtilForSp.formatToCustomString((Date) entry.get("createdAt")));
            entry.put("updatedAt", DateUtilForSp.formatToCustomString((Date) entry.get("updatedAt")));
        }
//todays end
        response.setCode(200);
        response.setMessage("Paginated results");
        CustomResponse.Result result = new CustomResponse.Result();
        result.setContent(content);
        result.setTotalElements(content.size());
        result.setTotalPages(1); // Assuming all data is fetched in one page
        result.setPage(0);
        result.setSize(content.size());
        result.setSort(Collections.singletonList(sortField + "," + sortOrder));
        response.setResult(result);

        return response;
    }

    public List<HouseKeeping> companyInfoDropDown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_get_3A_company_info_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }
}
