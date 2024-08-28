package com.khajana.setting.service.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.address.DistrictRequestDto;
import com.khajana.setting.dto.address.DistrictResponseDto;
import com.khajana.setting.dto.address.PoliceStationDistrictResponseDto;
import com.khajana.setting.dto.address.UpazilaDistrictResponseDto;
import com.khajana.setting.entity.address.District;
import com.khajana.setting.entity.address.Division;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.address.DistrictRepository;
import com.khajana.setting.repository.address.DivisionRepository;
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
public class DistrictService {

    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    DivisionRepository divisionRepository;

    public SimplePage<DistrictResponseDto> findAllDistricts(Pageable pageable) {
        Page<District> page = districtRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public DistrictResponseDto findDistrictById(Long id) {

        District newEntity = districtRepository.findDistrictById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTOFind(newEntity);
    }

    public ApiResponse addDistrict(DistrictRequestDto dto) {
        try {
            boolean typeExists = districtRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate District Name not allowed", "error");
            }
            District newEntity = districtRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public DistrictResponseDto updateDistrict(Long id, DistrictRequestDto dto) {
//        District newEntity = districtRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateDistrict(Long id, DistrictRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = districtRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate District   Name not allowed", "error");
            }
            District newEntity = districtRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<DistrictResponseDto> getDropDown() {
        List<District> page = districtRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteDistrict(Long id) {
        districtRepository.deleteDistrictById(id);
    }

    private District transferToEntity(Long id, DistrictRequestDto dto) {
        District district = new District();
        if (id != null && id > 0) {
            District newEntity = districtRepository.findDistrictById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            Division division = divisionRepository.findById(dto.getDivisionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Division", "Id", dto.getDivisionId()));
            district.setDivision(division);
            district.setId(newEntity.getId());
            district.setName(dto.getName());
            district.setNameBn(dto.getNameBn());
            district.setDivisionId(dto.getDivisionId());
            district.setLat(dto.getLat());
            district.setLongitude(dto.getLongitude());
            district.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            district.setActive(dto.getActive());
            district.setUpdatedAt(new Date());

            return district;
        } else {
            Division division = divisionRepository.findById(dto.getDivisionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Division", "Id", dto.getDivisionId()));
            district.setDivision(division);
            district.setName(dto.getName());
            district.setNameBn(dto.getNameBn());
            district.setDivisionId(dto.getDivisionId());
            district.setLat(null);
            district.setLongitude(null);
            district.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            district.setActive(dto.getActive());
            district.setCreatedAt(new Date());

            return district;
        }

    }

    private DistrictResponseDto transferToDTO(District entity) {
        DistrictResponseDto dto = new DistrictResponseDto();
        dto.setId(entity.getId());
        dto.setDivisionId(entity.getDivisionId());
        dto.setDivisionName(entity.getDivision().getName());
        dto.setDivisionNameBn(entity.getDivision().getNameBn());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setLat(entity.getLat());
        dto.setLongitude(entity.getLongitude());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        return dto;
    }

    private DistrictResponseDto transferToDTOFind(District entity) {
        DistrictResponseDto dto = new DistrictResponseDto();
        dto.setId(entity.getId());
        dto.setDivisionId(entity.getDivisionId());
        dto.setDivisionName(entity.getDivision().getName());
        dto.setDivisionNameBn(entity.getDivision().getNameBn());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setLat(entity.getLat());
        dto.setLongitude(entity.getLongitude());
        dto.setSeqNo(NumberFormat.get2DigitDecimal(entity.getSeqNo()));
        dto.setActive(entity.getActive());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setUpdatedAt(DateUtil.convertDateToString(entity.getUpdatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        List<UpazilaDistrictResponseDto> upazilaUpazilas = entity.getUpazilas().stream().map(upazila -> {
            UpazilaDistrictResponseDto upazilaUpazila = new UpazilaDistrictResponseDto();
            upazilaUpazila.setId(upazila.getId());
            upazilaUpazila.setName(upazila.getName());
            upazilaUpazila.setNameBn(upazila.getNameBn());
            ;
            return upazilaUpazila;
        }).collect(Collectors.toList());
        dto.setUpazilas(upazilaUpazilas);
        List<PoliceStationDistrictResponseDto> policePoliceStationPoliceStations = entity.getPoliceStation().stream().map(policePoliceStation -> {
            PoliceStationDistrictResponseDto policePoliceStationPoliceStation = new PoliceStationDistrictResponseDto();
            policePoliceStationPoliceStation.setId(policePoliceStation.getId());
            policePoliceStationPoliceStation.setName(policePoliceStation.getName());
            policePoliceStationPoliceStation.setNameBn(policePoliceStation.getNameBn());
            ;
            return policePoliceStationPoliceStation;
        }).collect(Collectors.toList());
        dto.setPoliceStations(policePoliceStationPoliceStations);
        return dto;
    }
}
