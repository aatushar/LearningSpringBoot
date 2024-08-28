package com.khajana.setting.service.productRequisition;

import com.khajana.setting.dto.productRequisition.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndentBossService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ApiResponse findIndentMasterById(Long id) {
        try {
            String sql = "EXEC [SP_GET_4B_INDENT_MASTER_DETAILS] ?";

            IndentBossDto indentBossDto = jdbcTemplate.query(sql, new Object[]{id}, new ResultSetExtractor<IndentBossDto>() {
                @Override
                public IndentBossDto extractData(ResultSet rs) throws SQLException {
                    IndentBossDto masterData = null;
                    List<IndentChildBossDto> childDataList = new ArrayList<>();

                    // Handle master data
                    if (rs.next()) {
                        masterData = new IndentBossDto();
                        masterData.setId(rs.getLong("Id"));
                        masterData.setOrderMasterId(rs.getLong("OrderMasterId"));
                        masterData.setIndentNumber(rs.getString("IndentNumber"));
                        masterData.setIndentDate(rs.getDate("IndentDate").toLocalDate());
                        masterData.setIsProductReq(rs.getBoolean("IsProductReq"));
                        masterData.setCompanyId(rs.getLong("CompanyId"));
                        masterData.setBranchId(rs.getLong("BranchId"));
                        masterData.setProdTypeId(rs.getLong("ProdTypeId"));
                        masterData.setProdTypeName(rs.getString("ProdTypeName"));
                        masterData.setProdTypeNameBn(rs.getString("ProdTypeNameBn"));
                        masterData.setProdCatId(rs.getLong("ProdCatId"));
                        masterData.setProdCatName(rs.getString("ProdCatName"));
                        masterData.setProdCatNameBn(rs.getString("ProdCatNameBn"));
                        masterData.setMasterGroupId(rs.getLong("MasterGroupId"));
                        masterData.setItmMstrGrpName(rs.getString("ItmMstrGrpName"));
                        masterData.setItmMstrGrpNameBn(rs.getString("ItmMstrGrpNameBn"));
                        masterData.setDemandStoreId(rs.getLong("DemandStoreId"));
                        masterData.setDemandSlName(rs.getString("DemandSlName"));
                        masterData.setDemandSlNameBn(rs.getString("DemandSlNameBn"));
                        masterData.setToStoreId(rs.getLong("ToStoreId"));
                        masterData.setToStoreName(rs.getString("ToStoreName"));
                        masterData.setToStoreNameBn(rs.getString("ToStoreNameBn"));
                        masterData.setDeptId(rs.getLong("DeptId"));
                        masterData.setDepartmentName(rs.getString("DepartmentName"));
                        masterData.setDepartmentNameBn(rs.getString("DepartmentNameBn"));
                        masterData.setPurpose(rs.getString("Purpose"));
                        masterData.setRemarks(rs.getString("Remarks"));
                        masterData.setRemarksBn(rs.getString("RemarksBn"));
                        masterData.setSubmittedBy(Long.valueOf(rs.getString("SubmittedBy")));
                        masterData.setSubmittedAt(rs.getDate("SubmittedAt"));
                        masterData.setRecommendedBy(Long.valueOf(rs.getString("RecommendedBy")));
                        masterData.setRecommendedAt(rs.getTimestamp("RecommendedAt"));
                        masterData.setApprovedBy(Long.valueOf(rs.getString("ApprovedBy")));
                        masterData.setApprovedAt(rs.getTimestamp("ApprovedAt"));
                        masterData.setApprovedStatus(Boolean.valueOf(rs.getString("ApprovedStatus")));
                        masterData.setIsForIssue(rs.getBoolean("IsForIssue"));
                        masterData.setIsIssued(rs.getBoolean("IsIssued"));
                        masterData.setIsForPurchase(rs.getBoolean("IsForPurchase"));
                        masterData.setIsIndentClosed(rs.getBoolean("IsIndentClosed"));
                        masterData.setIsProReqClose(rs.getBoolean("IsProReqClose"));

                        // Process child data
                        while (rs.next()) {
                            IndentChildBossDto childDto = new IndentChildBossDto();
                            childDto.setId(rs.getLong("Id")); // Ensure column names match
                            childDto.setItemInfoId(rs.getLong("ItemInfoId"));
                            childDto.setDisplayItemName(rs.getString("DisplayItemName"));
                            childDto.setDisplayItemNameBn(rs.getString("DisplayItemNameBn"));
                            childDto.setUomId(rs.getLong("UomId"));
                            childDto.setUomShortCode(rs.getString("UomShortCode"));
                            childDto.setRelativeFactor(rs.getDouble("RelativeFactor"));
                            childDto.setIndentQuantity(rs.getDouble("IndentQuantity"));
                            childDto.setConsumOrderQty(rs.getDouble("ConsumOrderQty"));
                            childDto.setIndentRemarks(rs.getString("IndentRemarks"));
                            childDto.setRequiredDate(rs.getDate("RequiredDate"));
                            childDto.setIndentRemarksBn(rs.getString("IndentRemarksBn"));

                            childDataList.add(childDto);
                        }
                    }

                    if (masterData != null) {
                        masterData.setChilds(childDataList);
                    }

                    return masterData;
                }

            });

            if (indentBossDto == null) {
                return new ApiResponse(404, "No Records Found!", null, "");
            }

            return new ApiResponse(200, "", null, indentBossDto);
        } catch (Exception e) {
            // Log the exception for better debugging
            e.printStackTrace();
            return new ApiResponse(500, e.getMessage(), null, "");
        }
    }
}
