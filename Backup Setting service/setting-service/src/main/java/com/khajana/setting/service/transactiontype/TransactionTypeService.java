package com.khajana.setting.service.transactiontype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.transactiontype.TransactionTypeActiveRequestDto;
import com.khajana.setting.dto.transactiontype.TransactionTypeRequestDto;
import com.khajana.setting.dto.transactiontype.TransactionTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.transactiontype.TransactionSourceType;
import com.khajana.setting.entity.transactiontype.TransactionType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.transactiontype.TransactionSourceTypeRepository;
import com.khajana.setting.repository.transactiontype.TransactionTypeRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
// @Transactional
public class TransactionTypeService {

    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    TransactionSourceTypeRepository transactionSourceTypeRepository;

    @Autowired
    TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private TransactionTypeRepository repository;

    // @Transactional
    public ApiResponse addTransactionType(TransactionTypeRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = repository.existsByTrnsTypeName(dto.getTrnsTypeName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Transaction  Type Name not allowed", "error");
            }
            TransactionType newEntity = repository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    /*
     * public Page<TransactionTypeDto> findAllTransactionTypes(Pageable pageable) {
     * Page<TransactionType> entities = repository.findAll(pageable); return
     * entities.map(this::transferToDTO); }
     */

    public List<TransactionTypeResponseDto> getDropDown() {
        List<TransactionType> page = repository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public SimplePage<TransactionTypeResponseDto> findAllTransactionTypes(Pageable pageable) {

        Page<TransactionType> page = repository.findAll(pageable);

        // Page<TransactionTypeResponseDto> page =
        // repository.getJoinInformation(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);

        /*
         * return new SimplePage<>(page.getContent() .stream() .map(this::transferToDTO)
         * .collect(Collectors.toList()), page.getTotalElements(), pageable);
         */
    }

    public ApiResponse updateTransactionType(Long id, TransactionTypeRequestDto dto) {

        try {
            boolean nameExistsForOtherId = repository.existsByTrnsTypeNameAndIdNot(dto.getTrnsTypeName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Transaction  Type Name not allowed", "error");
            }
            TransactionType newEntity = repository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }

    }

    public ApiResponse updateTransactionTypeStatus(Long id, TransactionTypeActiveRequestDto dto) {

        try {

            TransactionType newEntity = repository.save(transferToStatusEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }

    }

    public TransactionTypeResponseDto findTransactionTypeById(Long id) {

        TransactionType newEntity = repository.findTransactionTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);

        // TransactionTypeResponseDto dto = repository.findTransactionTypeById(id);
        // return dto;

    }

    public void deleteTransactionType(Long id) {
        repository.deleteTransactionTypeById(id);
    }

    TransactionType transferToEntity(Long id, TransactionTypeRequestDto dto) {
        TransactionType sourceType = new TransactionType();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            TransactionType newEntity = repository.findTransactionTypeById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            sourceType.setId(newEntity.getId());
            sourceType.setSourceTypeId(dto.getTrnsSourceTypeId());
            sourceType.setTrnsTypeName(dto.getTrnsTypeName());
            sourceType.setTrnsTypeNameBn(dto.getTrnsTypeNameBn());
            sourceType.setSeqNumber(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            sourceType.setIsActive(dto.getActive());
            sourceType.setUpdatedAt(new Date());
            sourceType.setUpdatedBy(userData.getId());
        } else {

            sourceType.setSourceTypeId(dto.getTrnsSourceTypeId());
            sourceType.setTrnsTypeName(dto.getTrnsTypeName());
            sourceType.setTrnsTypeNameBn(dto.getTrnsTypeNameBn());
            sourceType.setSeqNumber(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            sourceType.setIsActive(dto.getActive());
            sourceType.setCreatedAt(new Date());
            sourceType.setCreatedBy(userData.getId());
            // sourceType.setUpdatedAt(null);
            //  sourceType.setUpdatedBy(null);
        }

        return sourceType;
    }

    TransactionType transferToStatusEntity(Long id, TransactionTypeActiveRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();

        TransactionType transactionType = new TransactionType();
        if (id != null && id > 0) {
            TransactionType transactionTypeActive = transactionTypeRepository.findById(id).orElse(null);
            transactionTypeActive.setId(id);
            transactionTypeActive.setIsActive(dto.getActive());
            transactionTypeActive.setUpdatedAt(new Date());
            transactionTypeActive.setUpdatedBy(userData.getId());
            return transactionTypeActive;
        }
        return transactionType;

    }

    TransactionTypeResponseDto transferToDTO(TransactionType entity) {
        TransactionTypeResponseDto dto = new TransactionTypeResponseDto();
        dto.setId(entity.getId());
        dto.setTrnsSourceTypeId(entity.getSourceTypeId());
        if (entity.getSourceTypeId() != null) {
            TransactionSourceType transactionSourceType = transactionSourceTypeRepository
                    .findById(entity.getSourceTypeId()).orElse(null);
            dto.setTranSourceTypeName(transactionSourceType != null ? transactionSourceType.getTranSourceTypeName() : null);
            dto.setTranSourceTypeNameBN(transactionSourceType != null ? transactionSourceType.getTranSourceTypeNameBN() : null);
            //  dto.setTranSourceTypeName(transactionSourceType.getTranSourceTypeName());
            //dto.setTranSourceTypeNameBN(transactionSourceType.getTranSourceTypeNameBN());
        }
        dto.setTrnsTypeName(entity.getTrnsTypeName());
        dto.setTrnsTypeNameBn(entity.getTrnsTypeNameBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNumber()));
        dto.setActive(entity.getIsActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));

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
