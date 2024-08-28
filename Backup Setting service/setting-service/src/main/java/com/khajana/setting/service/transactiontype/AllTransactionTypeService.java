package com.khajana.setting.service.transactiontype;

import com.khajana.setting.dto.BranchWiseStore;
import com.khajana.setting.dto.DBCommonInsertAck;
import com.khajana.setting.dto.transactiontype.TransactionSubTypeResponseDto;
import com.khajana.setting.dto.transactiontype.TransactionTypeResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllTransactionTypeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<TransactionTypeResponseDto> getAllTransactionType(int pageNo, int pageSize) {
        String sqlt = """
                SELECT TT.[id] as 'id'
                				   ,TC.[id] as 'trnsSourceTypeId'
                				  ,TC.[tran_source_type_name] as 'trnsSourceTypeName'
                				  ,TC.[tran_source_type_name_bn] as 'trnsSourceSubTypeName'
                				  ,TT.[tran_source_type_id] as 'trnsSourceTypeId'

                				 ,TT.[tran_type_name] as 'trnsTypeName'

                				  ,TT.[tran_type_name_bn] as 'trnsTypeNameBn'
                				  ,TT.[seq_number] as 'seqNo'

                				  ,TT.[is_active] as 'status'

                				  ,TT.[created_at] as 'createdAt'
                				  , 1 as 'createdBy'
                				  ,TT.[created_by] as 'createdByName'
                			  FROM [Khajna].[dbo].[2C2_sv_tran_type] TT
                			  JOIN [Khajna].[dbo].[2C1_sv_tran_source_type] TC on TT.tran_source_type_id = TC.id
                  """;
        List<TransactionTypeResponseDto> transactionTypes = jdbcTemplate.query(sqlt,
                BeanPropertyRowMapper.newInstance(TransactionTypeResponseDto.class));

        return transactionTypes;
    }

    public List<TransactionSubTypeResponseDto> getAllTransactionSubTypes(int pageNo, int pageSize) {

        String sql = """
                SELECT TST.[id] as 'id'
                   ,TT.[id] as 'trnsTypeId'
                  ,TT.[tran_type_name] as 'trnsTypeName'
                  ,TT.[tran_type_name_bn] as 'trnsSubTypeName'
                  ,TST.[tran_type_id] as 'trnsTypeId'
                  ,TST.[tran_sub_type_name_bn] as 'trnsSubTypeNameBn'
                  ,TST.[seq_number] as 'seqNo'
                  ,TST.[created_at] as 'createdAt'
                  , 1 as 'createdBy'
                  ,TST.[created_by] as 'createdByName'
                 FROM [Khajna].[dbo].[2C3_sv_tran_sub_type] TST
                 JOIN [Khajna].[dbo].[2C2_sv_tran_type] TT on TST.tran_type_id = TT.id
                  """;
        List<TransactionSubTypeResponseDto> transactionSubTypes = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(TransactionSubTypeResponseDto.class));

        // transactionSubTypes.forEach(System.out :: println);

        return transactionSubTypes;
    }

    public DBCommonInsertAck insertTransactionType(int sourceTypeId, String typeNameEn, String typeNameBn) {

        String sql = "EXEC [dbo].[sp_insert_transaction_type] " + sourceTypeId + ",'" + typeNameEn + "'" + ",'"
                + typeNameBn + "'";

        // String sql = "EXEC sp_insert_transaction_type @SourceId = 1, @TypeNameEn =
        // 'test' , @TypeNameBn = 'test'";
        List<DBCommonInsertAck> commonInsertAcks = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(DBCommonInsertAck.class));

        return commonInsertAcks.get(0);
    }

    public List<BranchWiseStore> getAllBranchWiseStores(int pageNo, int pageSize) {
        String sql = "EXEC [dbo].[sp_get_branch_wise_store]";
        List<BranchWiseStore> branchWiseStores = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(BranchWiseStore.class));
        return branchWiseStores;
    }

    /*
     * @Autowired private AllTransactionTypeRepository transactionTypeRepository;
     *
     * public DBCommonInsertAck insertTransactionType(TransactionTypeRequest
     * insertDTO) {
     *
     * return
     * transactionTypeRepository.insertTransactionType(insertDTO.getSourceTypeId(),
     * insertDTO.getTrnsTypeNameEn(),insertDTO.getTrnsTypeNameBn()); }
     *
     * public List<TransactionTypeDto> getAllTransactionSubTypes(int pageNo, int
     * pageSize) {
     *
     * return transactionTypeRepository.getAllTransactionSubTypes(0,0); }
     *
     * public TransactionTypeDto getTransactionSubTypeById(int id) { return null; }
     *
     * public TransactionTypeDto updateTransactionSubType(TransactionTypeDto
     * postDto, int id) { return null; }
     *
     * public void deleteTransactionSubType(int id) {
     *
     * }
     *
     * public List<BranchWiseStore> getAllBranchWiseStores(int pageNo, int pageSize)
     * {
     *
     * return transactionTypeRepository.getAllBranchWiseStores(pageNo,pageSize); }
     */

}
