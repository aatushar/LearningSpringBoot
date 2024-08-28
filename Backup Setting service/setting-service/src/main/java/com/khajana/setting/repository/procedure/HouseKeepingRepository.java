package com.khajana.setting.repository.procedure;

import com.khajana.setting.dto.receive.ReceiveChildForWastageResponseDto;
import com.khajana.setting.entity.procedure.HouseKeeping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HouseKeepingRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<HouseKeeping> receiveDropdown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_receive_master_grn_date]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> legalDocumentDropdown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_3R1_legal_document_document]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> ImportLcInformationMasterDropdown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_4j_import_lc_basic_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> executeStoredProcedure() {
        return jdbcTemplate.query("EXEC [dbo].[trasSellDropdownHK]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("namebn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> customerDropDown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_customer_info_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> companyDropDown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_3b_company_branch_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> companyStoreDropDown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_3C1_company_store_location_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> vatStructureDropDown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_2E2_vat_structure_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> companyEmployeeDropdown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_3h1_company_employee_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> userCompanyStoreMappingDropDown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_3C6_user_company_store_mapping_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> vatMonthDropDown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_vat_month_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> companyListDropDown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_company_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> companySupplierListDropDown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_3k_supplier_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> itemMasterGroupDropdown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_3N01_item_master_group_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> itemGroupDropdown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_3N02_item_group_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> itemSubGroupDropdown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_3N03_item_sub_group_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> itemDropdown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_3N05_item_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> customerHavingIssue() {
        return jdbcTemplate.query("EXEC [dbo].[sp_vds_seller_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> iocProductTypeUomDropdown(Long ItemInfoId) {
        return jdbcTemplate.query("EXEC [dbo].[sp_ioc_product_type_uom_dropdown] ?", new Object[]{ItemInfoId}, (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> iocDropdown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_ioc_product_separated_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> fiscalYearVatMonthFromInputDate(java.sql.Date inputDate) {
        return jdbcTemplate.query("EXEC [dbo].[sp_fiscal_year_vat_month_from_input] ?", new Object[]{inputDate},
                (rs, rowNum) -> {
                    HouseKeeping houseKeeping = new HouseKeeping();
                    houseKeeping.setId(rs.getLong("id"));
                    houseKeeping.setType(rs.getString("type"));
                    houseKeeping.setName(rs.getString("name"));
                    houseKeeping.setNamebn(rs.getString("name_bn"));
                    return houseKeeping;
                });
    }

    public List<HouseKeeping> getHsCodeFromReceiveId(Long id) {
        return jdbcTemplate.query("EXEC [dbo].[sp_wastage_management_hs_code_get] ?", new Object[]{id},
                (rs, rowNum) -> {
                    HouseKeeping houseKeeping = new HouseKeeping();
                    houseKeeping.setId(rs.getLong("id"));
                    houseKeeping.setType(rs.getString("type"));
                    houseKeeping.setName(rs.getString("name"));
                    houseKeeping.setNamebn(rs.getString("name_bn"));
                    return houseKeeping;
                });
    }

    public List<HouseKeeping> getRecievedSupplierVatMonth() {
        return jdbcTemplate.query("EXEC [dbo].[sp_6d2_supplier_vat_month_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> getDamagedItemsHsCode() {
        return jdbcTemplate.query("EXEC [dbo].[sp_damaged_items_hs_code]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> departmentListDropDown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_company_section_department_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> ExportLcInformationMasterDropdown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_4E_export_lc_information_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> UtilizationDeclarationMasterDropdown() {
        return jdbcTemplate.query("EXEC [dbo].[sp_4F_utilization_declaration_dropdown]", (rs, rowNum) -> {
            HouseKeeping houseKeeping = new HouseKeeping();
            houseKeeping.setId(rs.getLong("id"));
            houseKeeping.setType(rs.getString("type"));
            houseKeeping.setName(rs.getString("name"));
            houseKeeping.setNamebn(rs.getString("name_bn"));
            return houseKeeping;
        });
    }

    public List<HouseKeeping> exportLcByCustomer(Long id) {
        return jdbcTemplate.query("EXEC [dbo].[sp_customer_wise_export_lc] @customerId = ?",
                new Object[]{id},
                (rs, rowNum) -> {
                    HouseKeeping houseKeeping = new HouseKeeping();
                    houseKeeping.setId(rs.getLong("id"));
                    houseKeeping.setType(rs.getString("type"));
                    houseKeeping.setName(rs.getString("name"));
                    houseKeeping.setNamebn(rs.getString("name_bn"));
                    return houseKeeping;
                });
    }

    public List<HouseKeeping> getHsCodeFromItemSubGroup(Long subGroupId) {
        return jdbcTemplate.query("EXEC [dbo].[hscodefromsubgroup] @subGroupId = ?",
                new Object[]{subGroupId},
                (rs, rowNum) -> {
                    HouseKeeping houseKeeping = new HouseKeeping();
                    houseKeeping.setId(rs.getLong("id"));
                    houseKeeping.setType(rs.getString("type"));
                    houseKeeping.setName(rs.getString("name"));
                    houseKeeping.setNamebn(rs.getString("name_bn"));
                    return houseKeeping;
                });
    }

    public List<ReceiveChildForWastageResponseDto> getItemsOfWastage(Long hsCodeId, Long ReceiveMasterId) {
        return jdbcTemplate.query(
                "EXEC [dbo].[sp_wastage_management_items_get] @hsCodeId = ?, @ReceiveMasterId = ?",
                new Object[]{hsCodeId, ReceiveMasterId},
                new BeanPropertyRowMapper<>(ReceiveChildForWastageResponseDto.class));
    }

    public List<ReceiveChildForWastageResponseDto> getItemsDamage(Long hsCodeId) {
        return jdbcTemplate.query(
                "EXEC [dbo].[sp_damaged_items_by_hs_code_from_issue_child] @hsCodeId = ?",
                new Object[]{hsCodeId},
                new BeanPropertyRowMapper<>(ReceiveChildForWastageResponseDto.class));
    }
    // public List<ReceiveChildForWastageResponseDto> getHsCodeFromItemSubGroup(Long subGroupId) {
    //     return jdbcTemplate.query(
    //             "EXEC [dbo].[sp_get_hs_code_from_item_sub_group] @SubGroupID  = ?",
    //             new Object[]{hsCodeId},
    //             new BeanPropertyRowMapper<>(ReceiveChildForWastageResponseDto.class));
    // }

}
