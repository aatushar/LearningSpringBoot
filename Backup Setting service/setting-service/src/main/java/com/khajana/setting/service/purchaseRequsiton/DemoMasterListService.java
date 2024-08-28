package com.khajana.setting.service.purchaseRequsiton;

import com.khajana.setting.dto.productRequisition.ApiResponse;
import com.khajana.setting.dto.productRequisition.ApiResponseResult;
import com.khajana.setting.dto.productRequisition.CustomKeyValue;
import com.khajana.setting.dto.productRequisition.CustomPageable;
import com.khajana.setting.dto.purchaseRequsition.DemoMasterList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DemoMasterListService {

    private final JdbcTemplate jdbcTemplate;

    public DemoMasterListService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ApiResponse getDemoMasterList(Long storeId, CustomPageable pageable) {
        try {
            // Validate pageable
            List<CustomKeyValue> errorMessages = validatePageable(pageable);
            if (!errorMessages.isEmpty()) {
                return new ApiResponse(400, "Bad Request", errorMessages, null);
            }

            // Build the SQL query
            String sql = "EXEC [SP_GET_4D_PURCHAGE_REQ_MASTER_LIST] ?, ?, ?, ?";
            List<DemoMasterList> data = jdbcTemplate.query(
                    sql,
                    new Object[]{storeId, pageable.getDbField(), pageable.getSortDirection(), pageable.getFilter()},
                    BeanPropertyRowMapper.newInstance(DemoMasterList.class)
            );

            // Calculate total pages
            int totalElements = data.size(); // In case your SP does not support total count, it will be the total elements
            int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

            // Create pageable response
            Pageable springPageable = PageRequest.of(pageable.getPageNo(), pageable.getPageSize());
            int start = (int) springPageable.getOffset();
            int end = Math.min((start + springPageable.getPageSize()), data.size());
            List<DemoMasterList> pageData = data.subList(start, end);

            ApiResponseResult result = new ApiResponseResult();
            result.setContent(pageData);
            result.setTotalElements(totalElements);
            result.setTotalPages(totalPages);
            result.setPage(pageable.getPageNo());
            result.setSize(pageable.getPageSize());
            result.setSort(java.util.Collections.singletonList(pageable.getDbField() + "," + pageable.getSortDirection()));

            return new ApiResponse(200, "Paginated Results", null, result);

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), null, null);
        }

    }

    private List<CustomKeyValue> validatePageable(CustomPageable pageable) {
        List<CustomKeyValue> errorMessages = new ArrayList<>();

        if (pageable.getPageNo() < 0) {
            errorMessages.add(new CustomKeyValue("pageNo", "Page number cannot be less than 0"));
        }
        if (pageable.getPageSize() <= 0) {
            errorMessages.add(new CustomKeyValue("pageSize", "Page size must be greater than 0"));
        }
        if (!"ASC".equalsIgnoreCase(pageable.getSortDirection()) && !"DESC".equalsIgnoreCase(pageable.getSortDirection())) {
            errorMessages.add(new CustomKeyValue("sortDirection", "Sort direction must be either ASC or DESC"));
        }

        return errorMessages;
    }

    public ApiResponse demoMasterListById(Long id) {
        try {
            String sql = "EXEC [SP_GET_4D_PURCHAGE_REQ_MASTER_LIST] ?, ?, ?, ?";
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, id);

            if (results == null || results.isEmpty()) {
                return new ApiResponse(404, "No Records Found!", null, "");
            }
            Map<String, Object> data = results.get(0);
            DemoMasterList dto = new DemoMasterList();

            dto.setId((Long) data.get("id"));
            dto.setRequisitionNumber((String) data.get("requisitionNumber"));
            dto.setRequisitionDate((String) data.get("requisitionDate"));
            dto.setCompanyId((Long) data.get("companyId"));
            dto.setBranchId((Long) data.get("branchId"));
            dto.setStoreId((Long) data.get("storeId"));
            dto.setProdTypeId((Long) data.get("prodTypeId"));
            dto.setProdTypeName((String) data.get("prodTypeName"));
            dto.setProdTypeNameBn((String) data.get("prodTypeNameBn"));
            dto.setMasterGroupId((Long) data.get("masterGroupId"));
            dto.setItmMstrGroupName((String) data.get("itmMstrGroupName"));
            dto.setItmMstrGrpNameBn((String) data.get("itmMstrGrpNameBn"));
            dto.setSubmittedBy((String) data.get("submittedBy"));
            dto.setSubmittedAt((String) data.get("submittedAt"));
            dto.setSubmittedByName((String) data.get("submittedByName"));

            return new ApiResponse(200, "", null, dto);
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), null, null);
        }
    }
}
