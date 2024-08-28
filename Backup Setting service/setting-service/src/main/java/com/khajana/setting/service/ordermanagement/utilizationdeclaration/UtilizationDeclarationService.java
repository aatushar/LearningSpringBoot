package com.khajana.setting.service.ordermanagement.utilizationdeclaration;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.ordermanagement.utilizationdeclaration.UtilizationDeclarationChildRequestDto;
import com.khajana.setting.dto.ordermanagement.utilizationdeclaration.UtilizationDeclarationMasterRequestDto;
import com.khajana.setting.dto.ordermanagement.utilizationdeclaration.UtilizationDeclarationMasterResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.customer.Customer;
import com.khajana.setting.entity.ordermanagement.exportlcinformation.ExportLcInformationMaster;
import com.khajana.setting.entity.ordermanagement.utilizationdeclaration.UtilizationDeclarationChild;
import com.khajana.setting.entity.ordermanagement.utilizationdeclaration.UtilizationDeclarationMaster;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.bank.BankInfoRepository;
import com.khajana.setting.repository.companybranch.CompanyRepository;
import com.khajana.setting.repository.companybranch.CompanySupplierRepository;
import com.khajana.setting.repository.currency.CurrencyRepository;
import com.khajana.setting.repository.customer.CustomerRepository;
import com.khajana.setting.repository.inventorymethod.InventoryMethodRepo;
import com.khajana.setting.repository.item.ItemGroupRepository;
import com.khajana.setting.repository.item.ItemRepository;
import com.khajana.setting.repository.ordermanagement.ExportLcInformationMasterRepository;
import com.khajana.setting.repository.ordermanagement.ImportLcInformationChildRepository;
import com.khajana.setting.repository.ordermanagement.utilizationdeclaration.UtilizationDeclarationChildRepository;
import com.khajana.setting.repository.ordermanagement.utilizationdeclaration.UtilizationDeclarationMasterRepository;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.repository.transactiontype.TransactionSourceTypeRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.ConstantValue;
import com.khajana.setting.utils.ContextUser;
import com.khajana.setting.utils.DateUtil;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilizationDeclarationService {

    @Autowired
    com.khajana.setting.repository.ordermanagement.ImportLcInformationMasterRepository ImportLcInformationMasterRepository;

    @Autowired
    UtilizationDeclarationMasterRepository utilizationDeclarationMasterRepository;

    @Autowired
    ImportLcInformationChildRepository ilicr;

    @Autowired
    ItemGroupRepository itemGroupRepository;

    @Autowired
    InventoryMethodRepo inventoryMethodRepo;

    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanySupplierRepository supplierRepo;
    @Autowired
    BankInfoRepository bankInfoRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TransactionSourceTypeRepository transactionSourceTypeRepository;

    @Autowired
    UtilizationDeclarationChildRepository utilizationDeclarationChildRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ExportLcInformationMasterRepository exportLcInformationMasterRepository;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;

    public SimplePage<UtilizationDeclarationMasterResponseDto> findAllUtilizationDeclarationMasters(Pageable pageable) {
        Page<UtilizationDeclarationMaster> page = utilizationDeclarationMasterRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public UtilizationDeclarationMasterResponseDto findUtilizationDeclarationMasterById(Long id) {

        UtilizationDeclarationMaster newEntity = utilizationDeclarationMasterRepository
                .findUtilizationDeclarationMasterById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransactionUtilizationDeclarationMaster", "Id", id));

        return transferToFindByIdDTO(newEntity);
    }

    public ApiResponse addUtilizationDeclarationMaster(UtilizationDeclarationMasterRequestDto dto) {
        // Read user id from JWT Token
        try {
            boolean typeExists = utilizationDeclarationMasterRepository.existsByUdRegisterNo(dto.getUdRegisterNo());

            if (typeExists) {
                return new ApiResponse(500, "Duplicate Ud Register Number not allowed", "validation error");
            }
            UtilizationDeclarationMaster newEntity = utilizationDeclarationMasterRepository
                    .save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateUtilizationDeclarationMaster(Long id, UtilizationDeclarationMasterRequestDto dto) {
        // Read user id from JWT Token
        try {
            UtilizationDeclarationMaster newEntity = utilizationDeclarationMasterRepository
                    .save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.UtilizationDeclarationMasterDropdown();
        return result;
    }

    public List<HouseKeeping> exportLcByCustomer(Long id) {
        List<HouseKeeping> result = houseKeepingRepository.exportLcByCustomer(id);
        return result;
    }

    public void deleteUtilizationDeclarationMaster(Long id) {
        utilizationDeclarationMasterRepository.deleteUtilizationDeclarationMasterById(id);
    }

    private UtilizationDeclarationMaster transferToEntity(Long id, UtilizationDeclarationMasterRequestDto dto) {
        UtilizationDeclarationMaster udm = new UtilizationDeclarationMaster();
        CustomUserDetails userData = ContextUser.getLoginUserData();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        udm.setLcId(dto.getLcId());
        udm.setUdRegisterNo(dto.getUdRegisterNo() != null ? dto.getUdRegisterNo() : "UD-" + timestamp);
        udm.setUdRegisterDate(dto.getUdRegisterDate());
        // udm.setBuyerCode(dto.getBuyerCode());
        udm.setRemarks(dto.getRemarks());
        udm.setCreatedAt(new Date());
        udm.setCreatedBy(userData.getId());

        UtilizationDeclarationMaster newUdm = utilizationDeclarationMasterRepository.save(udm);

        for (UtilizationDeclarationChildRequestDto udcr : dto.getItems()) {
            UtilizationDeclarationChild udc = new UtilizationDeclarationChild();
            udc.setUdRegisterId(newUdm.getId());
            udc.setBblcId(udcr.getBblcId());
            udc.setRemarks(udcr.getRemarks());
            udc.setCreatedAt(new Date());
            udc.setCreatedBy(userData.getId());
            utilizationDeclarationChildRepository.save(udc);
        }

        return udm;
    }

    private UtilizationDeclarationMasterResponseDto transferToDTO(UtilizationDeclarationMaster entity) {
        UtilizationDeclarationMasterResponseDto dto = new UtilizationDeclarationMasterResponseDto();
        dto.setId(entity.getId());

        dto.setLcId(entity.getLcId());
        if (entity.getLcId() != null) {
            ExportLcInformationMaster exportLcInformationMaster = exportLcInformationMasterRepository
                    .findById(entity.getLcId()).orElse(null);
            if (exportLcInformationMaster != null) {
                dto.setLcNo(exportLcInformationMaster.getLcNo());
                dto.setLcDate(DateUtil.convertDateToString(exportLcInformationMaster.getLcDate(),
                        ConstantValue.OUT_GOING_DATE_PATTERN));
                if (exportLcInformationMaster.getCustomerId() != null) {
                    Customer customer = customerRepository.findById(exportLcInformationMaster.getCustomerId())
                            .orElse(null);
                    if (customer != null && customer.getCustomerName() != null) {
                        dto.setCustomerId(customer.getId());
                        dto.setCustomerName(customer.getCustomerName());
                    }
                }
            }
        }
        dto.setUdRegisterNo(entity.getUdRegisterNo());
        dto.setUdRegisterDate(DateUtil.convertDateToString(entity.getUdRegisterDate(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
        // dto.setBuyerCode(entity.getBuyerCode());
        dto.setRemarks(entity.getRemarks());

        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(),
                ConstantValue.OUT_GOING_DATE_PATTERN));

        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }
        return dto;
    }

    private UtilizationDeclarationMasterResponseDto transferToFindByIdDTO(UtilizationDeclarationMaster entity) {
        UtilizationDeclarationMasterResponseDto dto = new UtilizationDeclarationMasterResponseDto();
        dto.setId(entity.getId());

        dto.setLcId(entity.getLcId());
        if (entity.getLcId() != null) {
            ExportLcInformationMaster exportLcInformationMaster = exportLcInformationMasterRepository
                    .findById(entity.getLcId()).orElse(null);
            if (exportLcInformationMaster != null) {
                dto.setLcNo(exportLcInformationMaster.getLcNo());
                dto.setLcDate(DateUtil.convertDateToString(exportLcInformationMaster.getLcDate(),
                        ConstantValue.OUT_GOING_DATE_PATTERN));
                if (exportLcInformationMaster.getCustomerId() != null) {
                    Customer customer = customerRepository.findById(exportLcInformationMaster.getCustomerId())
                            .orElse(null);
                    if (customer != null && customer.getCustomerName() != null) {
                        dto.setCustomerId(customer.getId());
                        dto.setCustomerName(customer.getCustomerName());
                    }
                }
            }
        }
        dto.setUdRegisterNo(entity.getUdRegisterNo());
        dto.setUdRegisterDate(DateUtil.convertDateToString(entity.getUdRegisterDate(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
        // dto.setBuyerCode(entity.getBuyerCode());
        dto.setRemarks(entity.getRemarks());

        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(),
                ConstantValue.OUT_GOING_DATE_PATTERN));
        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }

        return dto;
    }
}
