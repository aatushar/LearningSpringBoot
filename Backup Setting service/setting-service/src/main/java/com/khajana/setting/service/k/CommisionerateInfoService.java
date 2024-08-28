package com.khajana.setting.service.k;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.k.CommisionerateInfoRequestDto;
import com.khajana.setting.dto.k.CommisionerateInfoResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.k.CommisionerateInfo;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.k.CommisionerateInfoRepository;
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
public class CommisionerateInfoService {

    @Autowired
    CommisionerateInfoRepository commisionerateInfoRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<CommisionerateInfoResponseDto> findAllCommisionerateInfos(Pageable pageable) {
        Page<CommisionerateInfo> page = commisionerateInfoRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CommisionerateInfoResponseDto findCommisionerateInfoById(Long id) {

        CommisionerateInfo newEntity = commisionerateInfoRepository.findCommisionerateInfoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addCommisionerateInfo(CommisionerateInfoRequestDto dto) {
        try {
            boolean typeExists = commisionerateInfoRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Comissionerate Name not allowed", "error");
            }
            CommisionerateInfo newEntity = commisionerateInfoRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public CommisionerateInfoResponseDto updateCommisionerateInfo(Long id, CommisionerateInfoRequestDto dto) {
//        CommisionerateInfo newEntity = commisionerateInfoRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateCommisionerateInfo(Long id, CommisionerateInfoRequestDto dto) {
        // Read user id from JWT Token
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = commisionerateInfoRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Comissionerate Name not allowed", "error");
            }
            CommisionerateInfo newEntity = commisionerateInfoRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "OK", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CommisionerateInfoResponseDto> getDropDown() {
        List<CommisionerateInfo> page = commisionerateInfoRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCommisionerateInfo(Long id) {
        commisionerateInfoRepository.deleteCommisionerateInfoById(id);
    }

    private CommisionerateInfo transferToEntity(Long id, CommisionerateInfoRequestDto dto) {
        CommisionerateInfo commisionerateInfo = new CommisionerateInfo();
        CustomUserDetails userData = ContextUser.getLoginUserData();
        if (id != null && id > 0) {
            CommisionerateInfo newEntity = commisionerateInfoRepository.findCommisionerateInfoById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            commisionerateInfo.setId(newEntity.getId());
            commisionerateInfo.setName(dto.getName());
            commisionerateInfo.setNameBn(dto.getNameBn());
            commisionerateInfo.setCode(dto.getCode());
            commisionerateInfo.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            commisionerateInfo.setActive(dto.getActive());
            commisionerateInfo.setUpdatedAt(new Date());
            commisionerateInfo.setUpdatedBy(userData.getId());

            return commisionerateInfo;
        } else {
            commisionerateInfo.setName(dto.getName());
            commisionerateInfo.setNameBn(dto.getNameBn());
            commisionerateInfo.setCode(dto.getCode());
            commisionerateInfo.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            commisionerateInfo.setActive(dto.getActive());
            commisionerateInfo.setCreatedAt(new Date());
            commisionerateInfo.setCreatedBy(userData.getId());

            return commisionerateInfo;
        }

    }

    private CommisionerateInfoResponseDto transferToDTO(CommisionerateInfo entity) {
        CommisionerateInfoResponseDto dto = new CommisionerateInfoResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setCode(entity.getCode());
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
