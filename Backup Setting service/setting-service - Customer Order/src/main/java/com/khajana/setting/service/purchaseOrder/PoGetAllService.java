package com.khajana.setting.service.purchaseOrder;



import com.khajana.setting.dto.productRequisition.ApiResponseResult;

import com.khajana.setting.dto.productRequisition.CustomPageable;
import com.khajana.setting.dto.purchaseOrder.ApiResponse;

import com.khajana.setting.dto.purchaseOrder.CustomKeyValue;
import com.khajana.setting.dto.purchaseOrder.PoGetAllDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PoGetAllService {
    private final JdbcTemplate jdbcTemplate;

    public PoGetAllService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ApiResponse getPoGetAll(Long storeId, CustomPageable pageable) {
        try {
            // Validate pageable
            List<CustomKeyValue> errorMessages = validatePageable(pageable);
            if (!errorMessages.isEmpty()) {
                return new ApiResponse(400, "Bad Request", errorMessages, null);
            }

            // Build the SQL query
            String sql = "EXEC [SP_GET_4G_PURCHAGE_ORDER_MASTER_LIST]?, ?, ?, ?";
            List<PoGetAllDto> data = jdbcTemplate.query(
                    sql,
                    new Object[]{storeId, pageable.getDbField(), pageable.getSortDirection(), pageable.getFilter()},
                    BeanPropertyRowMapper.newInstance(PoGetAllDto.class)
            );

            // Calculate total pages
            int totalElements = data.size(); // In case your SP does not support total count, it will be the total elements
            int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

            // Create pageable response
            Pageable springPageable = PageRequest.of(pageable.getPageNo(), pageable.getPageSize());
            int start = (int) springPageable.getOffset();
            int end = Math.min((start + springPageable.getPageSize()), data.size());
            List<PoGetAllDto> pageData = data.subList(start, end);

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

    public ApiResponse poGetAllListById(Long id) {
        try {
            String sql = "EXEC [SP_GET_4G_PURCHAGE_ORDER_MASTER_LIST] ?, ?, ?, ?";
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, id, null, null, null);

            if (results == null || results.isEmpty()) {
                return new ApiResponse(404, "No Records Found!", null, "");
            }

            // Debugging - Log the results
            System.out.println("Results: " + results);

            Map<String, Object> data = results.get(0);
            PoGetAllDto dto = new PoGetAllDto();

            // Assign all fields from the Map to the DTO
            dto.setId((Long) data.get("id"));
            dto.setPurchaseReqMasterId((Long) data.get("purchaseReqMasterId"));
            dto.setPurchaseOrderNumber((String) data.get("purchaseOrderNumber"));
            dto.setCompanyId((Long) data.get("companyId"));
            dto.setBranchId((Long) data.get("branchId"));
            dto.setStoreId((Long) data.get("storeId"));
            dto.setPurchaseOrderType((String) data.get("purchaseOrderType"));
            dto.setMasterGroupId((Long) data.get("masterGroupId"));
            dto.setItmMstrGrpName((String) data.get("itmMstrGrpName"));
            dto.setItmMstrGrpNameBn((String) data.get("itmMstrGrpNameBn"));
            dto.setPurchaseOrderDate((Date) data.get("purchaseOrderDate"));
            dto.setSupplierId((Long) data.get("supplierId"));
            dto.setSupplierName((String) data.get("supplierName"));
            dto.setSupplierNameBn((String) data.get("supplierNameBn"));
            dto.setDeliveryPoint((String) data.get("deliveryPoint"));
            dto.setDeliveryDate((Date) data.get("deliveryDate"));
            dto.setPayTem((String) data.get("payTem"));
            dto.setPurchaseOrderAmount((Double) data.get("purchaseOrderAmount"));
            dto.setIsActive((Boolean) data.get("isActive"));
            dto.setCollectorId((Long) data.get("collectorId"));
            dto.setRemarks((String) data.get("remarks"));
            dto.setRemarksBn((String) data.get("remarksBn"));
            dto.setSubmittedBy((String) data.get("submittedBy"));
            dto.setSubmittedAt((Date) data.get("submittedAt"));
            dto.setRecommendedBy((String) data.get("recommendedBy"));
            dto.setRecommendedAt((Date) data.get("recommendedAt"));
            dto.setApprovedBy((String) data.get("approvedBy"));
            dto.setApprovedAt((Date) data.get("approvedAt"));
            dto.setApprovedStatus((Boolean) data.get("approvedStatus"));

            return new ApiResponse(200, "Record Found", null, dto);
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), null, null);
        }
    }
}