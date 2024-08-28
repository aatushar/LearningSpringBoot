package com.khajana.setting.service.bank;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.bank.BankInfoRequestDto;
import com.khajana.setting.dto.bank.BankInfoResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.bank.BankBranchInfo;
import com.khajana.setting.entity.bank.BankInfo;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
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
public class BankInfoService {

    @Autowired
    BankInfoRepository bankInfoRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    /*
     * public Page<BankInfoDto> findAllBankInfos(Pageable pageable) { Page<BankInfo>
     * page = bankInfoRepository.findAll(pageable); return
     * page.map(this::transferToDTO); }
     */

    public SimplePage<BankInfoResponseDto> findAllBankInfos(Pageable pageable) {
        Page<BankInfo> page = bankInfoRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public BankInfoResponseDto findBankInfoById(Long id) {

        BankInfo newEntity = bankInfoRepository.findBankInfoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    //    public BankInfoDto addBankInfo(BankInfoDto dto) {
//        BankInfo newEntity = bankInfoRepository.save(transferToEntity(dto));
//        returBankInfon  transferToDTO(newEntity);
//    }
    public ApiResponse addBankInfo(BankInfoRequestDto dto) {

        // Read user id from JWT Token
        try {
            boolean typeExists = bankInfoRepository.existsByBankName(dto.getBankName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Bank not allowed", "error");
            }
            BankInfo newEntity = bankInfoRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public BankInfoResponseDto updateBankInfo(Long id, BankInfoRequestDto dto) {
//        BankInfo newEntity = bankInfoRepository.save(transferToEntity(id,dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateBankInfo(Long id, BankInfoRequestDto dto) {
        try {
            boolean nameExistsForOtherId = bankInfoRepository.existsByBankNameAndIdNot(dto.getBankName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Bank not allowed", "error");
            }
            BankInfo newEntity = bankInfoRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, " ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<BankInfoResponseDto> getDropDown() {
        List<BankInfo> page = bankInfoRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteBankInfo(Long id) {
        bankInfoRepository.deleteBankInfoById(id);
    }

    private BankInfo transferToEntity(Long id, BankInfoRequestDto dto) {
        BankInfo bankInfo = new BankInfo();

        CustomUserDetails userData = ContextUser.getLoginUserData();

        if (id != null && id > 0) {
            BankInfo newEntity = bankInfoRepository.findBankInfoById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            bankInfo.setId(newEntity.getId());
            bankInfo.setBankName(dto.getBankName());
            bankInfo.setBankNameBn(dto.getBankNameBn());
            bankInfo.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            bankInfo.setActive(dto.getActive());
            bankInfo.setUpdatedAt(new Date());
            bankInfo.setUpdatedBy(userData.getId());

            return bankInfo;
        } else {
            bankInfo.setBankName(dto.getBankName());
            bankInfo.setBankNameBn(dto.getBankNameBn());
            bankInfo.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            bankInfo.setActive(dto.getActive());
            bankInfo.setCreatedAt(new Date());
            bankInfo.setCreatedBy(userData.getId());

            return bankInfo;
        }

    }

    private BankInfoResponseDto transferToDTO(BankInfo entity) {
        BankInfoResponseDto dto = new BankInfoResponseDto();
        dto.setId(entity.getId());
        dto.setBankName(entity.getBankName());
        dto.setBankNameBn(entity.getBankNameBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        if (entity.getBankBranchInfos() != null && !entity.getBankBranchInfos().isEmpty()) {
            List<BankBranchInfo> bankBranchInfos = entity.getBankBranchInfos().stream().map(bankBranchInfoNew -> {
                BankBranchInfo bankBranchInfo = new BankBranchInfo();
                bankBranchInfo.setId(bankBranchInfoNew.getId());
                bankBranchInfo.setBankBranchName(bankBranchInfoNew.getBankBranchName());
                bankBranchInfo.setBankBranchNameBn(bankBranchInfoNew.getBankBranchNameBn());
                bankBranchInfo.setBankBranchAddress(bankBranchInfoNew.getBankBranchAddress());
                bankBranchInfo.setBankBranchAddressBn(bankBranchInfoNew.getBankBranchAddressBn());
                bankBranchInfo.setBankBranchRoutingNo(bankBranchInfoNew.getBankBranchRoutingNo());
                bankBranchInfo.setBankBranchPhoneNumber(bankBranchInfoNew.getBankBranchPhoneNumber());
                bankBranchInfo.setBankBranchEmailAddress(bankBranchInfoNew.getBankBranchEmailAddress());
                bankBranchInfo.setSeqNo(bankBranchInfoNew.getSeqNo());
                bankBranchInfo.setIsActive(bankBranchInfoNew.getIsActive());
                return bankBranchInfo;
            }).collect(Collectors.toList());
            dto.setBankBranchInfos(bankBranchInfos);
        }
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
