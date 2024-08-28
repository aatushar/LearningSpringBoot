package com.khajana.setting.service.language;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.language.LanguageRequestDto;
import com.khajana.setting.dto.language.LanguageResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.language.Language;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.language.LanguageRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageService {

    private static final Logger logger = LoggerFactory.getLogger(LanguageService.class);
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<LanguageResponseDto> findAllLanguages(Pageable pageable) {
        Page<Language> page = languageRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public LanguageResponseDto findLanguageById(Long id) {

        Language newEntity = languageRepository.findLanguageById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addLanguage(LanguageRequestDto dto) {
        try {
            boolean typeExists = languageRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Language is not allowed.", "error");
            }
            Language newEntity = languageRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }


    public ApiResponse updateLanguage(Long id, LanguageRequestDto dto) {
        try {
            boolean nameExistsForOtherId = languageRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Language is not allowed", "error");
            }
            Language newEntity = languageRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            logger.error("Error adding treasury challan", e.getMessage());
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<LanguageResponseDto> getDropDown() {
        List<Language> page = languageRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteLanguage(Long id) {
        languageRepository.deleteLanguageById(id);
    }

    private Language transferToEntity(Long id, LanguageRequestDto dto) {
        Language language = new Language();
        CustomUserDetails userData = ContextUser.getLoginUserData();
        if (id != null && id > 0) {
            Language newEntity = languageRepository.findLanguageById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            language.setId(newEntity.getId());
            language.setName(dto.getName());
            language.setCode(dto.getCode());
            language.setAppLangCode(dto.getAppLangCode());
            language.setRtl(dto.getRtl());
            language.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            language.setActive(dto.getActive());
            language.setUpdatedAt(new Date());
            language.setUpdatedBy(userData.getId());

            return language;
        } else {
            language.setName(dto.getName());
            language.setCode(dto.getCode());
            language.setAppLangCode(dto.getAppLangCode());
            language.setRtl(dto.getRtl());
            language.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            language.setActive(dto.getActive());
            language.setCreatedAt(new Date());
            language.setCreatedBy(userData.getId());

            return language;
        }

    }

    private LanguageResponseDto transferToDTO(Language entity) {
        LanguageResponseDto dto = new LanguageResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setAppLangCode(entity.getAppLangCode());
        dto.setRtl(entity.getRtl());
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
