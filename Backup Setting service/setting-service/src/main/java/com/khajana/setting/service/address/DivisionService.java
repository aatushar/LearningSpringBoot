package com.khajana.setting.service.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.address.DistrictDivisionResponseDto;
import com.khajana.setting.dto.address.DivisionRequestDto;
import com.khajana.setting.dto.address.DivisionResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.address.Country;
import com.khajana.setting.entity.address.Division;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.address.CountryRepository;
import com.khajana.setting.repository.address.DivisionRepository;
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
public class DivisionService {

    @Autowired
    DivisionRepository divisionRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<DivisionResponseDto> findAllDivisions(Pageable pageable) {
        Page<Division> page = divisionRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public DivisionResponseDto findDivisionById(Long id) {

        Division newEntity = divisionRepository.findDivisionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTOFind(newEntity);
    }

    public ApiResponse addDivision(DivisionRequestDto dto) {
        try {
            boolean typeExists = divisionRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Division Name not allowed", "error");
            }
            Division newEntity = divisionRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public DivisionResponseDto updateDivision(Long id, DivisionRequestDto dto) {
//        Division newEntity = divisionRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateDivision(Long id, DivisionRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = divisionRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Division  Name not allowed", "error");
            }
            Division newEntity = divisionRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<DivisionResponseDto> getDropDown() {
        List<Division> page = divisionRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteDivision(Long id) {
        divisionRepository.deleteDivisionById(id);
    }

    private Division transferToEntity(Long id, DivisionRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        Division division = new Division();
        if (id != null && id > 0) {
            Country country = countryRepository.findById(dto.getCountryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Country", "Id", dto.getCountryId()));
            division.setCountry(country);
            division.setId(id);
            division.setName(dto.getName());
            division.setNameBn(dto.getNameBn());
            division.setCountryId(dto.getCountryId());
            division.setLat(null);
            division.setLongitude(null);
            division.setSeqNo(dto.getSeqNo());
            division.setActive(dto.getActive());
            division.setUpdatedAt(new Date());
            division.setUpdatedBy(userData.getId());

            return division;
        } else {
            Country country = countryRepository.findById(dto.getCountryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Country", "Id", dto.getCountryId()));
            division.setCountry(country);
            division.setName(dto.getName());
            division.setNameBn(dto.getNameBn());
            division.setCountryId(dto.getCountryId());
            division.setLat(null);
            division.setLongitude(null);
            division.setActive(dto.getActive());
            division.setSeqNo(dto.getSeqNo());

            division.setCreatedAt(new Date());
            division.setCreatedBy(userData.getId());

            return division;
        }

    }

    private DivisionResponseDto transferToDTO(Division entity) {
        DivisionResponseDto dto = new DivisionResponseDto();
        dto.setId(entity.getId());
        dto.setCountryId(entity.getCountryId());
        dto.setCountryName(entity.getCountry().getName());
        dto.setCountryNameBn(entity.getCountry().getNameBn());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setLat(entity.getLat());
        dto.setLongitude(entity.getLongitude());
        dto.setActive(entity.getActive());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
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

    private DivisionResponseDto transferToDTOFind(Division entity) {
        DivisionResponseDto dto = new DivisionResponseDto();
        dto.setId(entity.getId());
        dto.setCountryId(entity.getCountryId());
        dto.setCountryName(entity.getCountry().getName());
        dto.setCountryNameBn(entity.getCountry().getNameBn());
        dto.setName(entity.getName());
        dto.setSeqNo(entity.getSeqNo());
        dto.setActive(entity.getActive());
        dto.setNameBn(entity.getNameBn());
        dto.setLat(entity.getLat());
        dto.setLongitude(entity.getLongitude());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }
        List<DistrictDivisionResponseDto> districtDistricts = entity.getDistricts().stream().map(district -> {
            DistrictDivisionResponseDto districtDistrict = new DistrictDivisionResponseDto();
            districtDistrict.setId(district.getId());
            districtDistrict.setName(district.getName());
            districtDistrict.setNameBn(district.getNameBn());
            return districtDistrict;
        }).collect(Collectors.toList());
        dto.setDistricts(districtDistricts);
        return dto;
    }
}
