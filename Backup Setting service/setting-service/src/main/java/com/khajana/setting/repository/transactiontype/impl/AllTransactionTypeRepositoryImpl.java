package com.khajana.setting.repository.transactiontype.impl;

import com.khajana.setting.dto.BranchWiseStore;
import com.khajana.setting.dto.DBCommonInsertAck;
import com.khajana.setting.dto.transactiontype.TransactionTypeResponseDto;
import com.khajana.setting.repository.transactiontype.AllTransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllTransactionTypeRepositoryImpl implements AllTransactionTypeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<TransactionTypeResponseDto> getAllTransactionSubTypes(int pageNo, int pageSize) {
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

        // String sql = "EXEC [dbo].[sp_get_transaction_types]";
        List<TransactionTypeResponseDto> transactionSubTypes = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(TransactionTypeResponseDto.class));

        // transactionSubTypes.forEach(System.out :: println);

        return transactionSubTypes;
    }

    @Override
    public DBCommonInsertAck insertTransactionType(int sourceTypeId, String typeNameEn, String typeNameBn) {

        String sql = "EXEC [dbo].[sp_insert_transaction_type] " + sourceTypeId + ",'" + typeNameEn + "'" + ",'"
                + typeNameEn + "'";

        // String sql = "EXEC sp_insert_transaction_type @SourceId = 1, @TypeNameEn =
        // 'test' , @TypeNameBn = 'test'";
        List<DBCommonInsertAck> commonInsertAcks = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(DBCommonInsertAck.class));

        return commonInsertAcks.get(0);
    }

    @Override
    public List<BranchWiseStore> getAllBranchWiseStores(int pageNo, int pageSize) {
        String sql = "EXEC [dbo].[sp_get_branch_wise_store]";
        List<BranchWiseStore> branchWiseStores = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(BranchWiseStore.class));
        return branchWiseStores;
    }
}
