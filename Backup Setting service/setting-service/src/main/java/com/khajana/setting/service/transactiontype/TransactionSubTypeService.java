package com.khajana.setting.service.transactiontype;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.transactiontype.TransactionSubTypeRequestDto;
import com.khajana.setting.dto.transactiontype.TransactionSubTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.transactiontype.TransactionSubType;
import com.khajana.setting.entity.transactiontype.TransactionType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.transactiontype.TransactionSubTypeRepository;
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
public class TransactionSubTypeService {

    @Autowired
    TransactionSubTypeRepository transactionSubTypeRepository;
    @Autowired
    TransactionTypeRepository transactionTypeRepository;

    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<TransactionSubTypeResponseDto> findAllTransactionSubTypes(final Pageable pageable) {

        final Page<TransactionSubType> page = transactionSubTypeRepository.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);

        /*
         * final Page<TransactionSubTypeResponseDto> page =
         * transactionSubTypeRepository.getJoinInformation(pageable); return new
         * SimplePage<>( page.getContent(), page.getTotalElements(), pageable);
         */

        /*
         * return new SimplePage<>(page.getContent() //.stream()
         * //.map(this::transferToDTO) //.collect(Collectors.toList()),
         * page.getTotalElements(), pageable);
         */
    }

    public List<TransactionSubTypeResponseDto> getTransactionSubTypeDropDown() {
        List<TransactionSubType> page = transactionSubTypeRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public TransactionSubTypeResponseDto findTransactionSubTypeById(Long id) {

        TransactionSubType newEntity = transactionSubTypeRepository.findSourceTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);

        // TransactionSubTypeResponseDto dto =
        // transactionSubTypeRepository.findSourceTypeById(id);
        // return dto;
    }

    public ApiResponse addTransactionSubType(TransactionSubTypeRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = transactionSubTypeRepository.existsByTrnsSubTypeName(dto.getTrnsSubTypeName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Transaction Sub Type Name not allowed", "error");
            }

            TransactionSubType newEntity = transactionSubTypeRepository.save(transferToEntity(0L, dto));

            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateTransactionSubType(Long id, TransactionSubTypeRequestDto dto) {

        try {

            boolean nameExistsForOtherId = transactionSubTypeRepository.existsByTrnsSubTypeNameAndIdNot(dto.getTrnsSubTypeName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Transaction Sub Type Name not allowed", "error");
            }
            TransactionSubType newEntity = transactionSubTypeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }

    }

    TransactionSubType transferToEntity(Long id, TransactionSubTypeRequestDto dto) {
        TransactionSubType sourceType = new TransactionSubType();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            TransactionType tran = transactionTypeRepository.findTransactionTypeById(dto.getTrnsTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

            sourceType.setTransactionType(tran);
            sourceType.setId(id);
            sourceType.setTrnsTypeId(dto.getTrnsTypeId());
            sourceType.setTrnsSubTypeName(dto.getTrnsSubTypeName());
            sourceType.setTrnsSubTypeNameBn(dto.getTrnsSubTypeNameBn());
            sourceType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            sourceType.setActive(dto.getActive());
            sourceType.setUpdatedAt(new Date());
            sourceType.setUpdatedBy(userData.getId());

        } else {

            TransactionType tran = transactionTypeRepository.findTransactionTypeById(dto.getTrnsTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

            sourceType.setTransactionType(tran);
            sourceType.setTrnsTypeId(dto.getTrnsTypeId());
            sourceType.setTrnsSubTypeName(dto.getTrnsSubTypeName());
            sourceType.setTrnsSubTypeNameBn(dto.getTrnsSubTypeNameBn());
            sourceType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            sourceType.setActive(dto.getActive());
            sourceType.setCreatedAt(new Date());
            sourceType.setCreatedBy(userData.getId());
        }

        return sourceType;
    }

    TransactionSubTypeResponseDto transferToDTO(TransactionSubType entity) {
        TransactionSubTypeResponseDto dto = new TransactionSubTypeResponseDto();
        dto.setId(entity.getId());
        dto.setTrnsTypeId(entity.getTrnsTypeId());
//        if (entity.getTransactionType() != null) {
//            dto.setTrnsTypeName(entity.getTransactionType().getTrnsTypeName());
//            dto.setTrnsTypeNameBn(entity.getTransactionType().getTrnsTypeNameBn());
//        }
        if (entity.getTrnsTypeId() != null) {
            TransactionType newEntity = transactionTypeRepository
                    .findById(entity.getTrnsTypeId()).orElse(null);
            dto.setTrnsTypeName(newEntity != null ? newEntity.getTrnsTypeName() : null);
            dto.setTrnsTypeNameBn(newEntity != null ? newEntity.getTrnsTypeNameBn() : null);

        }

        dto.setTrnsSubTypeName(entity.getTrnsSubTypeName());
        dto.setTrnsSubTypeNameBn(entity.getTrnsSubTypeNameBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.getActive());
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
