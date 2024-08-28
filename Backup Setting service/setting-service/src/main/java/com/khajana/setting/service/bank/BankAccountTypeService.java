package com.khajana.setting.service.bank;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.bank.BankAccountTypeRequestDto;
import com.khajana.setting.dto.bank.BankAccountTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.bank.BankAccountType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.bank.BankAccountTypeRepository;
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
public class BankAccountTypeService {

    @Autowired
    BankAccountTypeRepository bankAccountTypeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    /*
     * public Page<BankAccountTypeDto> findAllBankAccountTypes(Pageable pageable) {
     * Page<BankAccountType> page = bankAccountTypeRepository.findAll(pageable);
     * return page.map(this::transferToDTO); }
     */

    public SimplePage<BankAccountTypeResponseDto> findAllBankAccountTypes(Pageable pageable) {
        Page<BankAccountType> page = bankAccountTypeRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public BankAccountTypeResponseDto findBankAccountTypeById(Long id) {

        BankAccountType newEntity = bankAccountTypeRepository.findBankAccountTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    // public BankAccountTypeDto addBankAccountType(BankAccountTypeDto dto) {
    // BankAccountType newEntity =
    // bankAccountTypeRepository.save(transferToEntity(dto));
    // returBankAccountTypen transferToDTO(newEntity);
    // }
    public ApiResponse addBankAccountType(BankAccountTypeRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = bankAccountTypeRepository.existsByBankAccountTypeName(dto.getBankAccountTypeName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Bank Account Type is not allowed", "error");
            }
            BankAccountType newEntity = bankAccountTypeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public BankAccountTypeResponseDto updateBankAccountType(Long id, BankAccountTypeRequestDto dto) {
//        BankAccountType newEntity = bankAccountTypeRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateBankAccountType(Long id, BankAccountTypeRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean nameExistsForOtherId = bankAccountTypeRepository.existsByBankAccountTypeNameAndIdNot(dto.getBankAccountTypeName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Bank Account Type is not allowed", "error");
            }
            BankAccountType newEntity = bankAccountTypeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "ok");
        }
    }

    public List<BankAccountTypeResponseDto> getDropDown() {
        List<BankAccountType> page = bankAccountTypeRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteBankAccountType(Long id) {
        bankAccountTypeRepository.deleteBankAccountTypeById(id);
    }

    private BankAccountType transferToEntity(Long id, BankAccountTypeRequestDto dto) {
        BankAccountType bankAccountType = new BankAccountType();

        CustomUserDetails userData = ContextUser.getLoginUserData();
        if (id != null && id > 0) {
            BankAccountType newEntity = bankAccountTypeRepository.findBankAccountTypeById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            bankAccountType.setId(newEntity.getId());
            bankAccountType.setBankAccountTypeName(dto.getBankAccountTypeName());
            bankAccountType.setBankAccountTypeNameBn(dto.getBankAccountTypeNameBn());
            bankAccountType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            bankAccountType.setActive(dto.getActive());
            bankAccountType.setUpdatedAt(new Date());
            bankAccountType.setUpdatedBy(userData.getId());

            return bankAccountType;
        } else {
            bankAccountType.setBankAccountTypeName(dto.getBankAccountTypeName());
            bankAccountType.setBankAccountTypeNameBn(dto.getBankAccountTypeNameBn());
            bankAccountType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            bankAccountType.setActive(dto.getActive());
            bankAccountType.setCreatedAt(new Date());
            bankAccountType.setCreatedBy(userData.getId());

            return bankAccountType;
        }

    }

    private BankAccountTypeResponseDto transferToDTO(BankAccountType entity) {
        BankAccountTypeResponseDto dto = new BankAccountTypeResponseDto();
        dto.setId(entity.getId());
        dto.setBankAccountTypeName(entity.getBankAccountTypeName());
        dto.setBankAccountTypeNameBn(entity.getBankAccountTypeNameBn());
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
