package com.khajana.setting.service.transactiontype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.transactiontype.TransactionSourceTypeRequestDto;
import com.khajana.setting.dto.transactiontype.TransactionSourceTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.transactiontype.TransactionSourceType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.transactiontype.TransactionSourceTypeRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionSourceTypeService {

    @Autowired
    UserCredentialRepository userCredentials;
    @Autowired
    private TransactionSourceTypeRepository repository;

    // TransactionSourceTypeDto

    public ApiResponse addTransactionSourceType(TransactionSourceTypeRequestDto dto) {

        // Read user id from JWT Token

        try {
            boolean typeExists = repository.existsByTranSourceTypeName(dto.getTranSourceTypeName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate TranSourceTypeName not allowed", "error");
            }
            TransactionSourceType newEntity = repository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse findAllTransactionSourceTypes(Pageable pageable) {

        try {

            Page<TransactionSourceType> page = repository.findAll(pageable);

            return new ApiResponse(200, "Paginated Results",
                    new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                            page.getTotalElements(), pageable));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<TransactionSourceTypeResponseDto> getDropDown() {
        List<TransactionSourceType> page = repository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public ApiResponse updateTransactionSourceType(Long id, TransactionSourceTypeRequestDto dto) {

        try {
            boolean nameExistsForOtherId = repository.existsByTranSourceTypeNameAndIdNot(dto.getTranSourceTypeName(), id);

            if (nameExistsForOtherId) {
                return new ApiResponse(400, "Duplicate TranSourceTypeName not allowed", "error");
            }
            TransactionSourceType newEntity = repository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }

    }

    public TransactionSourceTypeResponseDto findTransactionSourceTypeById(Long id) {

        TransactionSourceType newEntity = repository.findSourceTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public void deleteTransactionSourceType(Long id) {
        repository.deleteSourceTypeById(id);
    }

    TransactionSourceType transferToEntity(Long id, TransactionSourceTypeRequestDto dto) {
        TransactionSourceType sourceType = new TransactionSourceType();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            TransactionSourceType entity = repository.findSourceTypeById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            entity.setId(entity.getId());
            entity.setTranSourceTypeName(dto.getTranSourceTypeName());
            entity.setTranSourceTypeNameBN(dto.getTranSourceTypeNameBN());
            entity.setSeqNumber(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            entity.setActive(dto.getActive());
            entity.setUpdatedAt(new Date());
            entity.setUpdatedBy(userData.getId());

            return entity;

        } else {
            sourceType.setTranSourceTypeName(dto.getTranSourceTypeName());
            sourceType.setTranSourceTypeNameBN(dto.getTranSourceTypeNameBN());
            sourceType.setSeqNumber(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            sourceType.setActive(dto.getActive());
            sourceType.setCreatedAt(new Date());
            sourceType.setCreatedBy(userData.getId());
        }

        return sourceType;
    }

    TransactionSourceTypeResponseDto transferToDTO(TransactionSourceType entity) {
        TransactionSourceTypeResponseDto dto = new TransactionSourceTypeResponseDto();
        dto.setId(entity.getId());
        dto.setTranSourceTypeName(entity.getTranSourceTypeName());
        dto.setTranSourceTypeNameBN(entity.getTranSourceTypeNameBN());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNumber()));
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setActive(entity.isActive());

        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        if (entity.getUpdatedBy() != null) {
            User updatedByUser = userCredentials.findById(entity.getUpdatedBy()).orElse(null);
            dto.setUpdatedBy(updatedByUser != null ? updatedByUser.getName() : null);
        }

        return dto;
    }

}
