package com.khajana.setting.service.vat;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.vat.VatPaymentMethodeRequestDto;
import com.khajana.setting.dto.vat.VatPaymentMethodeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.transactiontype.TransactionSubType;
import com.khajana.setting.entity.vat.VatPaymentMethodeEntity;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.transactiontype.TransactionSubTypeRepository;
import com.khajana.setting.repository.vat.VatPaymentMethodeRepo;
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
public class VatPaymentMethodeService {

    @Autowired
    VatPaymentMethodeRepo vatPaymentMethodeRepo;

    @Autowired
    TransactionSubTypeRepository transactionSubTypeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    //    public VatPaymentMethodeDto addVatPaymentMethode(VatPaymentMethodeDto dto) {
//        VatPaymentMethodeEntity newEntity = vatPaymentMethodeRepo.save(transferToEntity(dto));
//        return  transferToDTO(newEntity);
//    }
    public ApiResponse addVatPaymentMethode(VatPaymentMethodeRequestDto dto) {
        try {

            boolean typeExists = vatPaymentMethodeRepo.existsByVatPaymentMethodName(dto.getVatPaymentMethodName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate VAT Payment method not allowed", "error");
            }
            VatPaymentMethodeEntity newEntity = vatPaymentMethodeRepo.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateVatePaymentMethode(Long id, VatPaymentMethodeRequestDto dto) {

        // Read user id from JWT Token
        try {
            boolean nameExistsForOtherId = vatPaymentMethodeRepo.existsByVatPaymentMethodNameAndIdNot(dto.getVatPaymentMethodName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate VAT Payment method not allowed", "error");
            }
            VatPaymentMethodeEntity newEntity = vatPaymentMethodeRepo.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }

    }

    public Page<VatPaymentMethodeResponseDto> findAllVatPaymentMethodes(Pageable pageable) {

        Page<VatPaymentMethodeEntity> page = vatPaymentMethodeRepo.findAll(pageable);

        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
        /*
         * Page<VatPaymentMethodeResponseDto> page =
         * vatPaymentMethodeRepo.getJoinInformation(pageable); return new SimplePage<>(
         * page.getContent(), page.getTotalElements(), pageable);
         */

        /*
         * return new SimplePage<>( page.getContent().stream().map(this::transferToDTO)
         * .collect(Collectors.toList()), page.getTotalElements(), pageable);
         */
    }

    public List<VatPaymentMethodeResponseDto> getDropDown() {
        List<VatPaymentMethodeEntity> page = vatPaymentMethodeRepo.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public VatPaymentMethodeResponseDto findVatPaymentMethodeById(Long id) {

        VatPaymentMethodeEntity newEntity = vatPaymentMethodeRepo.findVatPaymentTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);

        // return vatPaymentMethodeRepo.findVatPaymentMethodById(id);

    }

    VatPaymentMethodeEntity transferToEntity(Long id, VatPaymentMethodeRequestDto dto) {
        VatPaymentMethodeEntity sourceType = new VatPaymentMethodeEntity();
        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            VatPaymentMethodeEntity newEntity = vatPaymentMethodeRepo.findVatPaymentTypeById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            sourceType.setId(newEntity.getId());
            sourceType.setTranSubTypeId(dto.getTranSubTypeId());
            sourceType.setVatPaymentMethodName(dto.getVatPaymentMethodName());
            sourceType.setVatPaymentMethodNameBn(dto.getVatPaymentMethodNameBn());
            sourceType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            sourceType.setActive(dto.getActive());
            sourceType.setUpdatedAt(new Date());
            sourceType.setUpdatedBy(userData.getId());
            return sourceType;
        }
        sourceType.setTranSubTypeId(dto.getTranSubTypeId());
        sourceType.setVatPaymentMethodName(dto.getVatPaymentMethodName());
        sourceType.setVatPaymentMethodNameBn(dto.getVatPaymentMethodNameBn());
        sourceType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
        sourceType.setActive(dto.getActive());
        sourceType.setCreatedAt(new Date());
        sourceType.setCreatedBy(userData.getId());
        return sourceType;
    }

    VatPaymentMethodeResponseDto transferToDTO(VatPaymentMethodeEntity entity) {
        VatPaymentMethodeResponseDto dto = new VatPaymentMethodeResponseDto();
        dto.setId(entity.getId());
        dto.setTranSubTypeId(entity.getTranSubTypeId());
//        if (entity.getTranSubTypeId() != null) {
//            TransactionSubType transactionSubType = transactionSubTypeRepository.findById(entity.getTranSubTypeId()).orElse(null);
//            dto.setTrnsSubTypeName(transactionSubType.getTrnsSubTypeName());
//            dto.setTrnsSubTypeNameBn(transactionSubType.getTrnsSubTypeNameBn());
//        }
        if (entity.getTranSubTypeId() != null) {
            TransactionSubType newEntity = transactionSubTypeRepository
                    .findById(entity.getTranSubTypeId()).orElse(null);
            dto.setTrnsSubTypeName(newEntity != null ? newEntity.getTrnsSubTypeName() : null);
            dto.setTrnsSubTypeNameBn(newEntity != null ? newEntity.getTrnsSubTypeNameBn() : null);

        }

        dto.setVatPaymentMethodName(entity.getVatPaymentMethodName());
        dto.setVatPaymentMethodNameBn(entity.getVatPaymentMethodNameBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.isActive());
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
