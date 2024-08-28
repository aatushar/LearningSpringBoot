package com.khajana.setting.service.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.address.UpazilaRequestDto;
import com.khajana.setting.dto.address.UpazilaResponseDto;
import com.khajana.setting.entity.address.District;
import com.khajana.setting.entity.address.Upazila;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.address.DistrictRepository;
import com.khajana.setting.repository.address.UpazilaRepository;
import com.khajana.setting.utils.ConstantValue;
import com.khajana.setting.utils.DateUtil;
import com.khajana.setting.utils.NumberFormat;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UpazilaService {

    @Autowired
    UpazilaRepository upazilaRepository;
    @Autowired
    DistrictRepository districtRepository;

    public SimplePage<UpazilaResponseDto> findAllUpazilas(Pageable pageable) {
        Page<Upazila> page = upazilaRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public UpazilaResponseDto findUpazilaById(Long id) {

        Upazila newEntity = upazilaRepository.findUpazilaById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addUpazila(UpazilaRequestDto dto) {
        try {
            boolean typeExists = upazilaRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Upazilla Name not allowed", "error");
            }
            Upazila newEntity = upazilaRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public UpazilaResponseDto updateUpazila(Long id, UpazilaRequestDto dto) {
//        Upazila newEntity = upazilaRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateUpazila(Long id, UpazilaRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = upazilaRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Upazilla  Name not allowed", "error");
            }
            Upazila newEntity = upazilaRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }


    public List<UpazilaResponseDto> getDropDown() {
        List<Upazila> page = upazilaRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteUpazila(Long id) {
        upazilaRepository.deleteUpazilaById(id);
    }

    private Upazila transferToEntity(Long id, UpazilaRequestDto dto) {
        Upazila upazila = new Upazila();
        if (id != null && id > 0) {
            District district = districtRepository.findById(dto.getDistrictId())
                    .orElseThrow(() -> new ResourceNotFoundException("District", "Id", dto.getDistrictId()));
            upazila.setDistrict(district);
            upazila.setId(id);
            upazila.setName(dto.getName());
            upazila.setNameBn(dto.getNameBn());
            upazila.setDistrictId(dto.getDistrictId());
            upazila.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            upazila.setActive(dto.getActive());
            upazila.setUpdatedAt(new Date());

            return upazila;
        } else {
            District district = districtRepository.findById(dto.getDistrictId())
                    .orElseThrow(() -> new ResourceNotFoundException("District", "Id", dto.getDistrictId()));
            upazila.setDistrict(district);
            upazila.setName(dto.getName());
            upazila.setNameBn(dto.getNameBn());
            upazila.setDistrictId(dto.getDistrictId());
            upazila.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            upazila.setActive(dto.getActive());
            upazila.setCreatedAt(new Date());

            return upazila;
        }

    }

    private UpazilaResponseDto transferToDTO(Upazila entity) {
        UpazilaResponseDto dto = new UpazilaResponseDto();
        dto.setId(entity.getId());
        dto.setDistrictId(entity.getDistrictId());
        dto.setDistrictName(entity.getDistrict().getName());
        dto.setDistrictNameBn(entity.getDistrict().getNameBn());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        return dto;
    }
}
