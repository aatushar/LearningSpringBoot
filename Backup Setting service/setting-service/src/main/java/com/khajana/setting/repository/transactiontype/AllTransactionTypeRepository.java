package com.khajana.setting.repository.transactiontype;

import com.khajana.setting.dto.BranchWiseStore;
import com.khajana.setting.dto.DBCommonInsertAck;
import com.khajana.setting.dto.transactiontype.TransactionTypeResponseDto;

import java.util.List;

public interface AllTransactionTypeRepository {

    List<TransactionTypeResponseDto> getAllTransactionSubTypes(int pageNo, int pageSize);

    DBCommonInsertAck insertTransactionType(int sourceTypeId, String typeNameEn, String typeNameBn);

    List<BranchWiseStore> getAllBranchWiseStores(int pageNo, int pageSize);
}
