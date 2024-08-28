package com.khajana.setting.service.bank;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.bank.BankBranchInfoRequestDto;
import com.khajana.setting.dto.bank.BankBranchInfoResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.bank.BankBranchInfo;
import com.khajana.setting.entity.bank.BankInfo;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.bank.BankBranchInfoRepository;
import com.khajana.setting.repository.bank.BankInfoRepository;
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
public class BankBranchInfoService {

    @Autowired
    BankBranchInfoRepository bankBranchInfoRepository;
    @Autowired
    BankInfoRepository bankInfoRepository;

    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<BankBranchInfoResponseDto> findAllBankBranchInfos(Pageable pageable) {
        Page<BankBranchInfo> page = bankBranchInfoRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public BankBranchInfoResponseDto findBankBranchInfoById(Long id) {

        BankBranchInfo newEntity = bankBranchInfoRepository.findBankBranchInfoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionBankBranchbankBranchInfo", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addBankBranchInfo(BankBranchInfoRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = bankBranchInfoRepository.existsByBankBranchName(dto.getBankBranchName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Bank Branch not allowed", "error");
            }
            BankBranchInfo newEntity = bankBranchInfoRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateBankBranchInfo(Long id, BankBranchInfoRequestDto dto) {
        try {
            boolean nameExistsForOtherId = bankBranchInfoRepository.existsByBankBranchNameAndIdNot(dto.getBankBranchName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Bank Branch not allowed", "error");
            }
            BankBranchInfo newEntity = bankBranchInfoRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, " ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<BankBranchInfoResponseDto> getDropDown() {
        List<BankBranchInfo> page = bankBranchInfoRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteBankBranchInfo(Long id) {
        bankBranchInfoRepository.deleteBankBranchInfoById(id);
    }

    private BankBranchInfo transferToEntity(Long id, BankBranchInfoRequestDto dto) {
        BankBranchInfo bankBranchInfo = new BankBranchInfo();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            BankBranchInfo newEntity = bankBranchInfoRepository.findBankBranchInfoById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("TransactionBankBranchbankBranchInfo", "Id", id));
            BankInfo bankInfo = bankInfoRepository.findById(dto.getBankId())
                    .orElseThrow(() -> new ResourceNotFoundException("BankInfo", "Id", dto.getBankId()));


            bankBranchInfo.setId(newEntity.getId());
            bankBranchInfo.setBankInfo(bankInfo);
            bankBranchInfo.setBankId(dto.getBankId());
            bankBranchInfo.setBankBranchName(dto.getBankBranchName());
            bankBranchInfo.setBankBranchNameBn(dto.getBankBranchNameBn());
            bankBranchInfo.setBankBranchAddress(dto.getBankBranchAddress());
            bankBranchInfo.setBankBranchAddressBn(dto.getBankBranchAddressBn());
            bankBranchInfo.setBankBranchRoutingNo(dto.getBankBranchRoutingNo());
            bankBranchInfo.setBankBranchEmailAddress(dto.getBankBranchEmailAddress());
            bankBranchInfo.setBankBranchPhoneNumber(dto.getBankBranchPhoneNumber());
            bankBranchInfo.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            bankBranchInfo.setIsActive(dto.getActive());

            bankBranchInfo.setUpdatedAt(new Date());
            bankBranchInfo.setUpdatedBy(userData.getId());

            return bankBranchInfo;
        } else {
            BankInfo bankInfo = bankInfoRepository.findById(dto.getBankId())
                    .orElseThrow(() -> new ResourceNotFoundException("BankInfo", "Id", dto.getBankId()));
            bankBranchInfo.setBankInfo(bankInfo);
            bankBranchInfo.setBankId(dto.getBankId());
            bankBranchInfo.setBankBranchName(dto.getBankBranchName());
            bankBranchInfo.setBankBranchNameBn(dto.getBankBranchNameBn());
            bankBranchInfo.setBankBranchAddress(dto.getBankBranchAddress());
            bankBranchInfo.setBankBranchAddressBn(dto.getBankBranchAddressBn());
            bankBranchInfo.setBankBranchRoutingNo(dto.getBankBranchRoutingNo());
            bankBranchInfo.setBankBranchEmailAddress(dto.getBankBranchEmailAddress());
            bankBranchInfo.setBankBranchPhoneNumber(dto.getBankBranchPhoneNumber());
            bankBranchInfo.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            bankBranchInfo.setIsActive(dto.getActive());

            bankBranchInfo.setCreatedAt(new Date());
            bankBranchInfo.setCreatedBy(userData.getId());

            return bankBranchInfo;
        }

    }

    private BankBranchInfoResponseDto transferToDTO(BankBranchInfo entity) {

        BankBranchInfoResponseDto dto = new BankBranchInfoResponseDto();
        dto.setId(entity.getId());
        dto.setBankId(entity.getBankId());

        if (entity.getBankInfo() != null) {
            dto.setBankName(entity.getBankInfo().getBankName());
            dto.setBankNameBn(entity.getBankInfo().getBankNameBn());
        }

        dto.setBankBranchName(entity.getBankBranchName());
        dto.setBankBranchNameBn(entity.getBankBranchNameBn());
        dto.setBankBranchAddress(entity.getBankBranchAddress());
        dto.setBankBranchAddressBn(entity.getBankBranchAddressBn());
        dto.setBankBranchRoutingNo(entity.getBankBranchRoutingNo());
        dto.setBankBranchEmailAddress(entity.getBankBranchEmailAddress());
        dto.setBankBranchPhoneNumber(entity.getBankBranchPhoneNumber());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
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
