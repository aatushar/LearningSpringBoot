package com.khajana.setting.service.customerOrder;

import com.khajana.setting.dto.costomerOrder.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CoIndexService {

    private final JdbcTemplate jdbcTemplate;

    public CoIndexService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ApiResponse getCoIndex(Long storeId, CustomPageable pageable) {
        try {
            // Validate pageable
            List<CustomKeyValue> errorMessages = validatePageable(pageable);
            if (!errorMessages.isEmpty()) {
                return new ApiResponse(400, "Bad Request", errorMessages, null);
            }

            // Build the SQL query
            String sql = "EXEC [4A7_OrderList_Pending] @StoreId = ?";
            List<CostomerOrderIndexDto> data = jdbcTemplate.query(
                    sql,
                    new Object[]{storeId}, // Pass the storeId parameter
                    BeanPropertyRowMapper.newInstance(CostomerOrderIndexDto.class)
            );

            // Calculate total pages
            int totalElements = data.size();
            int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

            // Create pageable response
            Pageable springPageable = PageRequest.of(pageable.getPageNo(), pageable.getPageSize());
            int start = (int) springPageable.getOffset();
            int end = Math.min((start + springPageable.getPageSize()), totalElements);

            // Ensure that start is not greater than end
            if (start > end) {
                start = end;
            }

            List<CostomerOrderIndexDto> pageData = data.subList(start, end);

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

    public ApiResponse coIndexById(Long id) {
        try {
            String sql = "EXEC [4A7_OrderList_Pending]";
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, id);

            if (results == null || results.isEmpty()) {
                return new ApiResponse(404, "Not Records Found!", null, "");
            }

            Map<String, Object> data = results.get(0);
            CostomerOrderIndexDto dto = new CostomerOrderIndexDto();

            dto.setOrderDate(((java.sql.Date) data.get("orderDate")));

            dto.setCustomerName((String) data.get("customerName"));
            dto.setPhone((String) data.get("phone"));
            dto.setTypeName((String) data.get("typeName"));
            dto.setOrderPkgType((String) data.get("orderPkgType"));
            dto.setTotalAmount((BigDecimal) data.get("totalAmount"));
            dto.setPaymodeName((String) data.get("paymodeName"));
            dto.setSPODone((Boolean) data.get("SPODone"));
            dto.setReceiveStatus((String) data.get("receiveStatus"));
            dto.setPaymentStatus((String) data.get("paymentStatus"));
            dto.setOrderStatus((String) data.get("orderStatus"));

            return new ApiResponse(200, "", null, dto);
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), null, null);
        }
    }
}
