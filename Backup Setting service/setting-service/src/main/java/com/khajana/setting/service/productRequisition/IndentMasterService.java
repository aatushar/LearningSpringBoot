package com.khajana.setting.service.productRequisition;

import com.khajana.setting.dto.productRequisition.*;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class IndentMasterService {

    private final JdbcTemplate jdbcTemplate;

    public IndentMasterService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ApiResponse getIndentMasterList(Long storeId, CustomPageable pageable) {
        try {
            // Validate pageable
            List<CustomKeyValue> errorMessages = validatePageable(pageable);
            if (!errorMessages.isEmpty()) {
                return new ApiResponse(400, "Bad Request", errorMessages, null);
            }

            // Build the SQL query
            String sql = "EXEC [SP_GET_4B_INDENT_MASTER_LIST] ?, ?, ?, ?";
            List<IndentMasterDto> data = jdbcTemplate.query(
                    sql,
                    new Object[]{storeId, pageable.getDbField(), pageable.getSortDirection(), pageable.getFilter()},
                    BeanPropertyRowMapper.newInstance(IndentMasterDto.class)
            );

            // Calculate total pages
            int totalElements = data.size(); // In case your SP does not support total count, it will be the total elements
            int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

            // Create pageable response
            Pageable springPageable = PageRequest.of(pageable.getPageNo(), pageable.getPageSize());
            int start = (int) springPageable.getOffset();
            int end = Math.min((start + springPageable.getPageSize()), data.size());
            List<IndentMasterDto> pageData = data.subList(start, end);

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

    public ApiResponse findIndentMasterById(Long id) {
        try {
            String sql = "EXEC [SP_GET_4B_INDENT_MASTER_DETAILS] ?,?,?,?";
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, id);

            if (results == null || results.isEmpty()) {
                return new ApiResponse(404, "No Records Found!", null, "");
            }

            Map<String, Object> data = results.get(0);
            IndentMasterDto dto = new IndentMasterDto();

            dto.setId((Long) data.get("id"));
            dto.setOrderMasterId((Long) data.get("orderMasterId"));
            dto.setIndentNumber((String) data.get("indentNumber"));
            dto.setIndentDate(((java.sql.Date) data.get("indentDate")).toLocalDate());
            dto.setIsProductReq((Boolean) data.get("isProductReq"));
            dto.setCompanyId((Long) data.get("companyId"));
            dto.setBranchId((Long) data.get("branchId"));
            dto.setProdTypeId((Long) data.get("prodTypeId"));
            dto.setMasterGroupId((Long) data.get("masterGroupId"));
            dto.setItmMstrGrpName((String) data.get("itmMstrGrpName"));
            dto.setItmMstrGrpNameBn((String) data.get("itmMstrGrpNameBn"));
            dto.setDemandStoreId((Long) data.get("demandStoreId"));
            dto.setDemandSlName((String) data.get("demandSlName"));
            dto.setDemandSlNameBn((String) data.get("demandSlNameBn"));
            dto.setToStoreId((Long) data.get("toStoreId"));
            dto.setToStoreName((String) data.get("toStoreName"));
            dto.setToStoreNameBn((String) data.get("toStoreNameBn"));
            dto.setDeptId((Long) data.get("deptId"));
            dto.setPurpose((String) data.get("purpose"));
            dto.setRemarks((String) data.get("remarks"));
            dto.setRemarksBn((String) data.get("remarksBn"));
            dto.setSubmittedBy((Long) data.get("submittedBy"));
            dto.setSubmittedAt(((java.sql.Timestamp) data.get("submittedAt")).toLocalDateTime());
            dto.setRecommendedBy((Long) data.get("recommendedBy"));
            dto.setRecommendedAt(((java.sql.Timestamp) data.get("recommendedAt")).toLocalDateTime());
            dto.setApprovedBy((Long) data.get("approvedBy"));
            dto.setApprovedAt(((java.sql.Timestamp) data.get("approvedAt")).toLocalDateTime());
            dto.setApprovedStatus((Boolean) data.get("approvedStatus"));
            dto.setIsForIssue((Boolean) data.get("isForIssue"));
            dto.setIsIssued((Boolean) data.get("isIssued"));
            dto.setIsForPurchase((Boolean) data.get("isForPurchase"));
            dto.setIsIndentClosed((Boolean) data.get("isIndentClosed"));
            dto.setIsProReqClose((Boolean) data.get("isProReqClose"));

            return new ApiResponse(200, "", null, dto);
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), null, "");
        }
    }
}
