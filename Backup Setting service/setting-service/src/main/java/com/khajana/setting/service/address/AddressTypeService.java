package com.khajana.setting.service.address;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.address.AddressTypeRequestDto;
import com.khajana.setting.dto.address.AddressTypeResponseDto;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.khajana.setting.entity.address.AddressType;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.address.AddressTypeRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class AddressTypeService {

    @Autowired
    AddressTypeRepository addressTypeRepository;

    public SimplePage<AddressTypeResponseDto> findAllAddressTypes(Pageable pageable) {
        Page<AddressType> page = addressTypeRepository.findAll(pageable);
        return new SimplePage<>(page.getContent()
                .stream()
                .map(this::transferToDTO)
                .collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public AddressTypeResponseDto findAddressTypeById(Long id) {

        AddressType newEntity = addressTypeRepository.findAddressTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionVatRegSource", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addAddressType(AddressTypeRequestDto dto) {
        try {

            if (dto == null) {
                return new ApiResponse(404, "No Parameter Found!", "");
            }
            if (dto.getName() == null || dto.getName().isEmpty()) {
                return new ApiResponse(404, " Name is empty!", "");
            }
            if (dto.getNameBn() == null || dto.getNameBn().isEmpty()) {
                return new ApiResponse(404, " NameBn is empty!", "");
            }
            if (dto.getActive() == null) {
                return new ApiResponse(404, "Active Status is required in Boolean!", "");
            }
            if (dto.getSeqNo() == null || dto.getSeqNo().toString().isEmpty()) {
                return new ApiResponse(404, "Sequence Number is required!", "");
            }
            AddressType newEntity = addressTypeRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "Data Inserted Successfully", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "");
        }
    }

    public AddressTypeResponseDto updateAddressType(Long id, AddressTypeRequestDto dto) {
        AddressType newEntity = addressTypeRepository.save(transferToEntity(id, dto));
        return transferToDTO(newEntity);
    }

    public List<AddressTypeResponseDto> getDropDown() {
        List<AddressType> page = addressTypeRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteAddressType(Long id) {
        addressTypeRepository.deleteAddressTypeById(id);
    }

    private AddressType transferToEntity(Long id, AddressTypeRequestDto dto) {
        AddressType addressType = new AddressType();
        if (id != null && id > 0) {
            addressType.setId(id);
            addressType.setName(dto.getName());
            addressType.setNameBn(dto.getNameBn());
            addressType.setSeqNo(dto.getSeqNo());
            addressType.setActive(dto.getActive());

            return addressType;
        } else {
            addressType.setName(dto.getName());
            addressType.setNameBn(dto.getNameBn());
            addressType.setSeqNo(dto.getSeqNo());
            addressType.setActive(dto.getActive());

            return addressType;
        }

    }

    private AddressTypeResponseDto transferToDTO(AddressType entity) {
        AddressTypeResponseDto dto = new AddressTypeResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNameBn(entity.getNameBn());
        dto.setSeqNo(entity.getSeqNo());
        dto.setActive(entity.isActive());
        return dto;
    }
}
