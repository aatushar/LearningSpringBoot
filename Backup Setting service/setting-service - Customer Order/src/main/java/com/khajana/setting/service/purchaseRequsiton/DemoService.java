package com.khajana.setting.service.purchaseRequsiton;

import com.khajana.setting.dto.productRequisition.ApiResponse;
import com.khajana.setting.dto.purchaseRequsition.DemoChildDto;
import com.khajana.setting.dto.purchaseRequsition.DemoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DemoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ApiResponse findDemoByID(Long id){
        try {
            String sql = "EXEC [SP_GET_4D_PURCHAGE_REQ_DETAILS]?";

            DemoDto demoDto = jdbcTemplate.query(sql, new Object[]{id}, new ResultSetExtractor<DemoDto>() {
                @Override
                public DemoDto extractData(ResultSet rs) throws SQLException {
                    DemoDto masterData = null;
                    List<DemoChildDto> childDataList = new ArrayList<>();

                    while (rs.next()) {
                        if (masterData == null) {
                            // Initialize master data on the first iteration
                            masterData = new DemoDto();
                            masterData.setId(rs.getLong("Id"));
                            masterData.setRequisitonNumber(rs.getString("RequisitionNumber"));
                            masterData.setRequisitionDate(rs.getString("RequisitionDate"));
                            masterData.setCompanyId(rs.getLong("CompanyId"));
                            masterData.setBranchId(rs.getLong("BranchId"));
                            masterData.setStoreId(rs.getLong("StoreId"));
                            masterData.setProdTypeId(rs.getLong("ProdTypeId"));
                            masterData.setProdTypeName(rs.getString("ProdTypeName"));
                            masterData.setProdTypeNameBn(rs.getString("ProdTypeNameBn"));
                            masterData.setProdCatId(rs.getString("ProdCatId"));
                            masterData.setProdCatName(rs.getString("ProdCatName"));
                            masterData.setProdCatNameBn(rs.getString("ProdCatNameBn"));
                            masterData.setMasterGroupId(rs.getLong("MasterGroupId"));
                            masterData.setItmMstrGrpName(rs.getString("ItmMstrGrpName"));
                            masterData.setItmMstrGrpNameBn(rs.getString("ItmMstrGrpNameBn"));
                            masterData.setSubmittedBy(rs.getString("SubmittedBy"));
                            masterData.setSubmittedAt(rs.getString("SubmittedAt"));
                            masterData.setSubmittedByName(rs.getString("SubmittedByName"));
                            masterData.setRecommendedBy(rs.getString("RecommendedBy"));
                            masterData.setRecommendedByName(rs.getString("RecommendedByName"));
                            masterData.setRecommendedAt(rs.getString("RecommendedAt"));
                            masterData.setApprovedBy(rs.getString("ApprovedBy"));
                            masterData.setApprovedByName(rs.getString("ApprovedByName"));
                            masterData.setApprovedAt(rs.getString("ApprovedAt"));
                            masterData.setApprovedStatus(rs.getBoolean("ApprovedStatus"));
                            masterData.setIsPartial(rs.getBoolean("IsPartial"));
                            masterData.setIsPurReqClosed(rs.getBoolean("IsPurReqClosed"));
                            masterData.setIsActive(rs.getBoolean("IsActive"));
                            masterData.setRemarks(rs.getString("Remarks"));
                            masterData.setRemarksBn(rs.getString("RemarksBn"));
                        }

                        // Process child data
                        DemoChildDto childDto = new DemoChildDto();
                        childDto.setPurchaseReqChildId(rs.getLong("PurchaseReqChildId"));
                        childDto.setPurchaseReqMasterId(rs.getLong("PurchaseReqMasterId"));
                        childDto.setItemInfoId(rs.getLong("ItemInfoId"));
                        childDto.setDisplayItemName(rs.getString("DisplayItemName"));
                        childDto.setDisplayItemNameBn(rs.getString("DisplayItemNameBn"));
                        childDto.setUomId(rs.getLong("UomId"));
                        childDto.setUomShortCode(rs.getString("UomShortCode"));
                        childDto.setRelativeFactor(rs.getDouble("RelativeFactor"));
                        childDto.setRate(rs.getDouble("Rate"));
                        childDto.setReqQuantity(rs.getDouble("ReqQuantity"));
                        childDto.setRequiredDate(rs.getDate("RequiredDate"));
                        childDto.setReqRemarks(rs.getString("ReqRemarks"));
                        childDto.setReqRemarksBn(rs.getString("ReqRemarksBn"));

                        childDataList.add(childDto);
                    }

                    if (masterData != null) {
                        masterData.setChilds(childDataList);
                    }

                    return masterData;
                }
            });
            if (demoDto == null) {
                return new ApiResponse(404, "no Records Found!", null, "");
            }
            return new ApiResponse(200, "", null, demoDto);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResponse(500, e.getMessage(), null, "");
        }
    }

}
