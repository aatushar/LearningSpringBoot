package com.khajana.setting.service.k;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.k.AccountCodeInfoRequestDto;
import com.khajana.setting.dto.k.AccountCodeInfoResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.k.AccountCodeInfo;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.k.AccountCodeInfoRepository;
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
public class AccountCodeInfoService {

    @Autowired
    AccountCodeInfoRepository accountCodeInfoRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<AccountCodeInfoResponseDto> findAllAccountCodeInfos(Pageable pageable) {
        Page<AccountCodeInfo> page = accountCodeInfoRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public AccountCodeInfoResponseDto findAccountCodeInfoById(Long id) {

        AccountCodeInfo newEntity = accountCodeInfoRepository.findAccountCodeInfoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addAccountCodeInfo(AccountCodeInfoRequestDto dto) {
        try {
            boolean typeExists = accountCodeInfoRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Transaction account Code not allowed.", "error");
            }
            AccountCodeInfo newEntity = accountCodeInfoRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

//    public AccountCodeInfoResponseDto updateAccountCodeInfo(Long id, AccountCodeInfoRequestDto dto) {
//        AccountCodeInfo newEntity = accountCodeInfoRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }

    public ApiResponse updateAccountCodeInfo(Long id, AccountCodeInfoRequestDto dto) {
        // Read user id from JWT Token
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = accountCodeInfoRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Transaction account Code not allowed", "error");
            }
            AccountCodeInfo newEntity = accountCodeInfoRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK ", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<AccountCodeInfoResponseDto> getDropDown() {
        List<AccountCodeInfo> page = accountCodeInfoRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteAccountCodeInfo(Long id) {
        accountCodeInfoRepository.deleteAccountCodeInfoById(id);
    }

    private AccountCodeInfo transferToEntity(Long id, AccountCodeInfoRequestDto dto) {
        AccountCodeInfo accountCodeInfo = new AccountCodeInfo();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            AccountCodeInfo newEntity = accountCodeInfoRepository.findAccountCodeInfoById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            accountCodeInfo.setId(newEntity.getId());
            accountCodeInfo.setName(dto.getName());
            accountCodeInfo.setNameBn(dto.getNameBn());
            accountCodeInfo.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            accountCodeInfo.setActive(dto.getActive());
            accountCodeInfo.setUpdatedAt(new Date());
            accountCodeInfo.setUpdatedBy(userData.getId());

            return accountCodeInfo;
        } else {
            accountCodeInfo.setName(dto.getName());
            accountCodeInfo.setNameBn(dto.getNameBn());
            accountCodeInfo.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            accountCodeInfo.setActive(dto.getActive());
            accountCodeInfo.setCreatedAt(new Date());
            accountCodeInfo.setCreatedBy(userData.getId());

            return accountCodeInfo;
        }

    }

    private AccountCodeInfoResponseDto transferToDTO(AccountCodeInfo entity) {
        AccountCodeInfoResponseDto dto = new AccountCodeInfoResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
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
