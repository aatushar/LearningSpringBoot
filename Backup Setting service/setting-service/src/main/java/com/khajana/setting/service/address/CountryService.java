package com.khajana.setting.service.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.address.CountryRequestDto;
import com.khajana.setting.dto.address.CountryResponseDto;
import com.khajana.setting.dto.address.DivisionCountryResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.address.Country;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.address.CountryRepository;
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
public class CountryService {

    @Autowired
    CountryRepository countryRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<CountryResponseDto> findAllCountrys(Pageable pageable) {
        Page<Country> page = countryRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public CountryResponseDto findCountryById(Long id) {

        Country newEntity = countryRepository.findCountryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTOFind(newEntity);
    }

    public ApiResponse addCountry(CountryRequestDto dto) {
        try {
            boolean typeExists = countryRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Country Name not allowed", "error");
            }
            Country newEntity = countryRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public CountryResponseDto updateCountry(Long id, CountryRequestDto dto) {
//        Country newEntity = countryRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateCountry(Long id, CountryRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = countryRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Country Name not allowed", "error");
            }
            Country newEntity = countryRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<CountryResponseDto> getDropDown() {
        List<Country> page = countryRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteCountry(Long id) {
        countryRepository.deleteCountryById(id);
    }

    private Country transferToEntity(Long id, CountryRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        Country country = new Country();
        if (id != null && id > 0) {
            country.setId(id);
            country.setName(dto.getName());
            country.setNameBn(dto.getNameBn());
            country.setCode(dto.getCode());
            country.setCodeBn(dto.getCodeBn());
            country.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            country.setActive(dto.getActive());
            country.setUpdatedAt(new Date());

            return country;
        } else {
            country.setName(dto.getName());
            country.setNameBn(dto.getNameBn());
            country.setCode(dto.getCode());
            country.setCodeBn(dto.getCodeBn());
            country.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            country.setActive(dto.getActive());
            country.setCreatedAt(new Date());
            country.setCreatedBy(userData.getId());

            return country;
        }

    }

    private CountryResponseDto transferToDTO(Country entity) {
        CountryResponseDto dto = new CountryResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setCode(entity.getCode());
        dto.setCodeBn(entity.getCodeBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }
        return dto;
    }

    private CountryResponseDto transferToDTOFind(Country entity) {
        CountryResponseDto dto = new CountryResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setCode(entity.getCode());
        dto.setCodeBn(entity.getCodeBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }
        List<DivisionCountryResponseDto> divisionDivisions = entity.getDivision().stream().map(division -> {
            DivisionCountryResponseDto divisionDivision = new DivisionCountryResponseDto();
            divisionDivision.setId(division.getId());
            divisionDivision.setName(division.getName());
            divisionDivision.setNameBn(division.getNameBn());
            return divisionDivision;
        }).collect(Collectors.toList());
        dto.setDivision(divisionDivisions);
        return dto;
    }
}
