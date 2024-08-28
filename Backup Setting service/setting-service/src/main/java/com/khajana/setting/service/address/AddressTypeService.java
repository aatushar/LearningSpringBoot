package com.khajana.setting.service.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.address.AddressTypeRequestDto;
import com.khajana.setting.dto.address.AddressTypeResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.address.AddressType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.address.AddressTypeRepository;
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
public class AddressTypeService {

    @Autowired
    AddressTypeRepository addressTypeRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    public SimplePage<AddressTypeResponseDto> findAllAddressTypes(Pageable pageable) {
        Page<AddressType> page = addressTypeRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public AddressTypeResponseDto findAddressTypeById(Long id) {

        AddressType newEntity = addressTypeRepository.findAddressTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addAddressType(AddressTypeRequestDto dto) {
        try {
            boolean typeExists = addressTypeRepository.existsByName(dto.getName());

            if (typeExists) {
                return new ApiResponse(400, "Duplicate Address Type is not allowed", "error");
            }
            AddressType newEntity = addressTypeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    //    public AddressTypeResponseDto updateAddressType(Long id, AddressTypeRequestDto dto) {
//        AddressType newEntity = addressTypeRepository.save(transferToEntity(id, dto));
//        return transferToDTO(newEntity);
//    }
    public ApiResponse updateAddressType(Long id, AddressTypeRequestDto dto) {
        try {
            if (id == null || id <= 0) {
                return new ApiResponse(404, "invalid id", "");
            }
            boolean nameExistsForOtherId = addressTypeRepository.existsByNameAndIdNot(dto.getName(), id);

            if (nameExistsForOtherId) {
                // If the name already exists for a different ID, return a validation message
                return new ApiResponse(400, "Duplicate Address Type is not allowed", "error");
            }
            AddressType newEntity = addressTypeRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<AddressTypeResponseDto> getDropDown() {
        List<AddressType> page = addressTypeRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteAddressType(Long id) {
        addressTypeRepository.deleteAddressTypeById(id);
    }

    private AddressType transferToEntity(Long id, AddressTypeRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        AddressType addressType = new AddressType();
        if (id != null && id > 0) {
            AddressType newEntity = addressTypeRepository.findAddressTypeById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));
            addressType.setId(newEntity.getId());
            addressType.setName(dto.getName());
            addressType.setNameBn(dto.getNameBn());
            addressType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            addressType.setActive(dto.getActive());
            addressType.setUpdatedAt(new Date());
            addressType.setUpdatedBy(userData.getId());

            return addressType;
        } else {
            addressType.setName(dto.getName());
            addressType.setNameBn(dto.getNameBn());
            addressType.setSeqNo(NumberFormat.get2DigitDecimal(dto.getSeqNo()));
            addressType.setActive(dto.getActive());
            addressType.setCreatedAt(new Date());
            addressType.setCreatedBy(userData.getId());

            return addressType;
        }

    }

    private AddressTypeResponseDto transferToDTO(AddressType entity) {
        AddressTypeResponseDto dto = new AddressTypeResponseDto();
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
