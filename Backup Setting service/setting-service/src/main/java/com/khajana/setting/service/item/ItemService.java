package com.khajana.setting.service.item;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.item.*;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.address.Country;
import com.khajana.setting.entity.companybranch.Company;
import com.khajana.setting.entity.currency.Currency;
import com.khajana.setting.entity.hscode.HsCode;
import com.khajana.setting.entity.item.*;
import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.entity.product.ProductType;
import com.khajana.setting.entity.uom.Uom;
import com.khajana.setting.entity.vat.VatPaymentMethodeEntity;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.address.CountryRepository;
import com.khajana.setting.repository.companybranch.CompanyRepository;
import com.khajana.setting.repository.currency.CurrencyRepository;
import com.khajana.setting.repository.hscode.HsCodeRepository;
import com.khajana.setting.repository.item.*;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import com.khajana.setting.repository.product.ProductTypeRepository;
import com.khajana.setting.repository.uom.UomRepository;
import com.khajana.setting.repository.uom.UomSetRepository;
import com.khajana.setting.repository.user.UserRepository;
import com.khajana.setting.repository.vat.VatPaymentMethodeRepo;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.ConstantValue;
import com.khajana.setting.utils.ContextUser;
import com.khajana.setting.utils.DateUtil;
import com.khajana.setting.utils.SimplePage;

import io.micrometer.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemStoreMappingRepository itemStoreMappingRepository;

    @Autowired
    ItemMasterGroupRepository itemMasterGroupRepository;
    @Autowired
    ItemGroupRepository itemGroupRepository;
    @Autowired
    ItemSubGroupRepository itemSubGroupRepository;
    @Autowired
    VatPaymentMethodeRepo vatpaymentMethodeRepo;
    @Autowired
    HsCodeRepository hsCodeRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UomSetRepository uomSetRepository;
    @Autowired
    UomRepository uomRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    HouseKeepingRepository houseKeepingRepository;
    @Autowired
    CountryRepository countryRepository;

    public SimplePage<ItemResponseDto> findAllItems(String filter, Pageable pageable) {
        Page<Item> page;
        if (StringUtils.isNotEmpty(filter)) {

            page = itemRepository.findAllItemByDisplayItmNameContaining(filter, pageable);
        } else {
            
            page = itemRepository.findAll(pageable);
        }
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public ItemFindByIdResponseDto findItemById(Long id) {

        Item newEntity = itemRepository.findItemById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Date", "Id", id));

        return transferToFindByIdDTO(newEntity);
    }

    public List<ServiceItemResponseDto> getServiceItemsByRebatable(boolean isRebateable) {
        List<Item> items;
        if (isRebateable == true) {
            items = itemRepository.findByIsRebateable(true);
        } else {
            items = itemRepository.findByIsRebateable(false);
        }
        return items.stream()
                .map(this::mapToServiceItemResponseDto)
                .collect(Collectors.toList());
    }

    private ServiceItemResponseDto mapToServiceItemResponseDto(Item item) {
        ServiceItemResponseDto dto = new ServiceItemResponseDto();
        dto.setItemInfoId(item.getId());
        dto.setDisplayItmName(item.getDisplayItmName());
        dto.setDisplayItmNameBn(item.getDisplayItmNameBn());
        dto.setCurrentRate(item.getCurrentRate());
        dto.setIsRebateable(item.getIsRebateable());
        dto.setIsTradeItem(item.getIsTradeItem());
        dto.setUomId(item.getUomId());
        if (item.getUomId() != null) {
            Uom uom = uomRepository.findUomById(item.getUomId()).orElse(null);
            if (uom != null && uom.getUomShortCode() != null) {
                dto.setUomName(uom.getUomShortCode());
            }
        }

        return dto;
    }

    public ApiResponse addItem(ItemRequestDto dto) {
        // Read user id from JWT Token
        Item item = new Item();
        CustomUserDetails userData = ContextUser.getLoginUserData();
        try {

            item.setCompanyId(1l);
            item.setVatPaymentMethodId(dto.getVatPaymentMethodId());
            item.setItmCode(dto.getItmCode());
            item.setDisplayItmCode(dto.getItmCode());
            item.setDisplayItmCodeBn(dto.getItmCode());
            item.setDisplayItmName(dto.getDisplayItmName());
            item.setDisplayItmNameBn(dto.getDisplayItmNameBn());
            item.setMushakItmName(dto.getDisplayItmName());
            item.setMushakItmNameBn(dto.getDisplayItmNameBn());
            item.setCountryOrigin(dto.getCountryOrigin());
            item.setHsCodeId(dto.getHsCodeId());
            item.setCurrencyInfoId(dto.getCurrencyInfoId());
            item.setUomId(dto.getUomId());
            item.setTrnsUnitId(dto.getTrnsUnitId());
            item.setStockUnitId(dto.getStockUnitId());
            item.setSalesUnitId(dto.getSalesUnitId());
            item.setIsRebateable(dto.getIsRebateable());
            item.setIsTradeItem(dto.getIsTradeItem());

            item.setActive(dto.getActive());
            item.setCreatedAt(new Date());
            item.setCreatedBy(userData.getId());

            Item insertedItem = itemRepository.save(item);

            if (dto.getMapper() != null) {
                ItemStoreMapping itemStoreMapping = new ItemStoreMapping();
                itemStoreMapping.setItemInfoId(insertedItem.getId());
                itemStoreMapping.setProdTypeId(dto.getMapper().getProdTypeId());
                itemStoreMapping.setItmSubGrpId(dto.getMapper().getItmGrpSubId());
                itemStoreMapping.setBranchId(dto.getMapper().getBranchId());
                itemStoreMapping.setStoreId(dto.getMapper().getStoreId());
                itemStoreMapping.setCreatedAt(new Date());
                itemStoreMapping.setCreatedBy(userData.getId());

                itemStoreMappingRepository.save(itemStoreMapping);
            }
            // Item newEntity = itemRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(insertedItem));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public ApiResponse updateItem(Long id, ItemUpdateRequestDto dto) {
        // Read user id from JWT Token
        Item item = new Item();
        CustomUserDetails userData = ContextUser.getLoginUserData();
        try {
            item.setId(id);
            item.setCompanyId(1l);
            item.setVatPaymentMethodId(dto.getVatPaymentMethodId());
            item.setItmCode(dto.getItmCode());
            item.setDisplayItmCode(dto.getItmCode());
            item.setDisplayItmCodeBn(dto.getItmCode());
            item.setDisplayItmName(dto.getDisplayItmName());
            item.setDisplayItmNameBn(dto.getDisplayItmNameBn());
            item.setMushakItmName(dto.getDisplayItmName());
            item.setMushakItmNameBn(dto.getDisplayItmNameBn());
            item.setCountryOrigin(dto.getCountryOrigin());
            item.setHsCodeId(dto.getHsCodeId());
            item.setCurrencyInfoId(dto.getCurrencyInfoId());
            item.setUomId(dto.getUomId());
            item.setTrnsUnitId(dto.getTrnsUnitId());
            item.setStockUnitId(dto.getStockUnitId());
            item.setSalesUnitId(dto.getSalesUnitId());
            item.setIsRebateable(dto.getIsRebateable());
            item.setIsTradeItem(dto.getIsTradeItem());

            item.setActive(dto.getActive());
            item.setUpdatedAt(new Date());
            item.setUpdatedBy(userData.getId());

            Item insertedItem = itemRepository.save(item);

            if (dto.getMapper() != null) {
                ItemStoreMapping itemStoreMapping = new ItemStoreMapping();
                itemStoreMapping.setId(dto.getMapper().getId());
                itemStoreMapping.setItemInfoId(insertedItem.getId());
                itemStoreMapping.setProdTypeId(dto.getMapper().getProdTypeId());
                itemStoreMapping.setItmSubGrpId(dto.getMapper().getItmGrpSubId());
                itemStoreMapping.setBranchId(dto.getMapper().getBranchId());
                itemStoreMapping.setStoreId(dto.getMapper().getStoreId());
                itemStoreMapping.setUpdatedAt(new Date());
                itemStoreMapping.setUpdatedBy(userData.getId());

                itemStoreMappingRepository.save(itemStoreMapping);
            }
            // Item newEntity = itemRepository.save(transferToEntity(id, dto));
            return new ApiResponse(200, "ok", transferToDTO(insertedItem));

        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public List<HouseKeeping> getDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.itemDropdown();
        return result;
    }

    public List<HouseKeeping> getHsCodeFromItemSubGroup(Long subGroupId) {
        return houseKeepingRepository.getHsCodeFromItemSubGroup(subGroupId);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteItemById(id);
    }

    private ItemResponseDto transferToDTO(Item entity) {
        ItemResponseDto dto = new ItemResponseDto();
        dto.setId(entity.getId());

        dto.setCompanyId(entity.getCompanyId());
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            dto.setCompanyName(company != null ? company.getCompName() : null);
        }
        dto.setVatPaymentMethodId(entity.getVatPaymentMethodId());
        if (entity.getVatPaymentMethodId() != null) {
            VatPaymentMethodeEntity vatPaymentMethod = vatpaymentMethodeRepo.findById(entity.getVatPaymentMethodId())
                    .orElse(null);
            dto.setVatPaymentMethodName(vatPaymentMethod != null ? vatPaymentMethod.getVatPaymentMethodName() : null);
        }
        dto.setItmCode(entity.getItmCode());
        dto.setDisplayItmCode(entity.getItmCode());
        dto.setDisplayItmCodeBn(entity.getItmCode());
        dto.setDisplayItmName(entity.getDisplayItmName());
        dto.setDisplayItmNameBn(entity.getDisplayItmNameBn());
        dto.setMushakItmName(entity.getDisplayItmName());
        dto.setMushakItmNameBn(entity.getDisplayItmNameBn());
        dto.setCountryOrigin(entity.getCountryOrigin());
        if (entity.getCountryOrigin() != null) {
            Country country = countryRepository.findById(entity.getCountryOrigin()).orElse(null);
            dto.setCountryName(country.getName());
        }
        dto.setHsCodeId(entity.getHsCodeId());
        if (entity.getHsCodeId() != null) {
            HsCode hsCode = hsCodeRepository.findById(entity.getHsCodeId()).orElse(null);
            dto.setHsCodeName(hsCode != null ? hsCode.getHsCode() : null);
        }
        dto.setCurrencyInfoId(entity.getCurrencyInfoId());
        if (entity.getCurrencyInfoId() != null) {
            Currency currency = currencyRepository.findById(entity.getCurrencyInfoId()).orElse(null);
            dto.setCurrencyInfoName(currency != null ? currency.getCurrencyShortCode() : null);
        }
        dto.setUomId(entity.getUomId());
        if (entity.getUomId() != null) {
            Uom uom = uomRepository.findById(entity.getUomId()).orElse(null);
            dto.setUomName(uom != null ? uom.getUomDesc() : null);
        }
        dto.setTrnsUnitId(entity.getTrnsUnitId());
        if (entity.getTrnsUnitId() != null) {
            Uom uom = uomRepository.findById(entity.getTrnsUnitId()).orElse(null);
            dto.setTrnsUnitName(uom != null ? uom.getUomDesc() : null);
        }
        dto.setStockUnitId(entity.getStockUnitId());
        if (entity.getStockUnitId() != null) {
            Uom uom = uomRepository.findById(entity.getStockUnitId()).orElse(null);
            dto.setStockUnitName(uom != null ? uom.getUomDesc() : null);
        }
        dto.setSalesUnitId(entity.getSalesUnitId());
        if (entity.getSalesUnitId() != null) {
            Uom uom = uomRepository.findById(entity.getSalesUnitId()).orElse(null);
            dto.setSalesUnitName(uom != null ? uom.getUomDesc() : null);
        }
        dto.setIsRebateable(entity.getIsRebateable());
        dto.setIsTradeItem(entity.getIsTradeItem());

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
        ItemStoreMapping itemStoreMapping = itemStoreMappingRepository.findByItemInfoId(entity.getId())
                .orElse(null);
        if (itemStoreMapping != null) {

        }

        if (itemStoreMapping != null) {
            ItemSubGroup itemSubGroup = itemSubGroupRepository.findById(itemStoreMapping.getItmSubGrpId()).orElse(null);
            if (itemSubGroup != null) {
                dto.setItmGrpSubId(itemSubGroup.getId());
                dto.setItmGrpSubName(itemSubGroup.getItmSubGrpName());

                ItemGroup itemGroup = itemSubGroup.getItemGroup();
                if (itemGroup != null) {
                    dto.setItmGrpId(itemGroup.getId());
                    dto.setItmGrpName(itemGroup.getItmGrpName());

                    ItemMasterGroup itemMasterGroup = itemGroup.getItemMasterGroup();
                    if (itemMasterGroup != null) {
                        dto.setItmMstrGrpId(itemMasterGroup.getId());
                        dto.setItmMstrGrpName(itemMasterGroup.getItmMstrGrpName());

                        ProductType productType = productTypeRepository.findById(itemStoreMapping.getProdTypeId())
                                .orElse(null);
                        if (productType != null) {
                            dto.setProdTypeId(productType.getId());
                            dto.setProdTypeName(productType.getName());
                            if (productType.getProductCategory() != null) {
                                dto.setCategoryId(productType.getProductCategory().getId());
                                dto.setCategoryName(productType.getProductCategory().getName());
                            }
                        }
                    }
                }
            }
        }

        return dto;
    }

    private ItemFindByIdResponseDto transferToFindByIdDTO(Item entity) {
        ItemFindByIdResponseDto dto = new ItemFindByIdResponseDto();
        // Set basic fields directly from the entity
        dto.setId(entity.getId());

        dto.setCompanyId(entity.getCompanyId());
        if (entity.getCompanyId() != null) {
            Company company = companyRepository.findById(entity.getCompanyId()).orElse(null);
            dto.setCompanyName(company != null ? company.getCompName() : null);
        }
        dto.setVatPaymentMethodId(entity.getVatPaymentMethodId());
        if (entity.getVatPaymentMethodId() != null) {
            VatPaymentMethodeEntity vatPaymentMethod = vatpaymentMethodeRepo.findById(entity.getVatPaymentMethodId())
                    .orElse(null);
            dto.setVatPaymentMethodName(vatPaymentMethod != null ? vatPaymentMethod.getVatPaymentMethodName() : null);
        }
        dto.setItmCode(entity.getItmCode());
        dto.setDisplayItmCode(entity.getItmCode());
        dto.setDisplayItmCodeBn(entity.getItmCode());
        dto.setDisplayItmName(entity.getDisplayItmName());
        dto.setDisplayItmNameBn(entity.getDisplayItmNameBn());
        dto.setMushakItmName(entity.getDisplayItmName());
        dto.setMushakItmNameBn(entity.getDisplayItmNameBn());
        dto.setCountryOrigin(entity.getCountryOrigin());
        if (entity.getCountryOrigin() != null) {
            Country country = countryRepository.findById(entity.getCountryOrigin()).orElse(null);
            dto.setCountryName(country.getName());
        }
        dto.setHsCodeId(entity.getHsCodeId());
        if (entity.getHsCodeId() != null) {
            HsCode hsCode = hsCodeRepository.findById(entity.getHsCodeId()).orElse(null);
            dto.setHsCodeName(hsCode != null ? hsCode.getHsCode() : null);
        }
        dto.setCurrencyInfoId(entity.getCurrencyInfoId());
        if (entity.getCurrencyInfoId() != null) {
            Currency currency = currencyRepository.findById(entity.getCurrencyInfoId()).orElse(null);
            dto.setCurrencyInfoName(currency != null ? currency.getCurrencyShortCode() : null);
        }
        dto.setUomId(entity.getUomId());
        if (entity.getUomId() != null) {
            Uom uom = uomRepository.findById(entity.getUomId()).orElse(null);
            dto.setUomName(uom != null ? uom.getUomDesc() : null);
        }
        dto.setTrnsUnitId(entity.getTrnsUnitId());
        if (entity.getTrnsUnitId() != null) {
            Uom uom = uomRepository.findById(entity.getTrnsUnitId()).orElse(null);
            dto.setTrnsUnitName(uom != null ? uom.getUomDesc() : null);
        }
        dto.setStockUnitId(entity.getStockUnitId());
        if (entity.getStockUnitId() != null) {
            Uom uom = uomRepository.findById(entity.getStockUnitId()).orElse(null);
            dto.setStockUnitName(uom != null ? uom.getUomDesc() : null);
        }
        dto.setSalesUnitId(entity.getSalesUnitId());
        if (entity.getSalesUnitId() != null) {
            Uom uom = uomRepository.findById(entity.getSalesUnitId()).orElse(null);
            dto.setSalesUnitName(uom != null ? uom.getUomDesc() : null);
        }
        dto.setIsRebateable(entity.getIsRebateable());
        dto.setIsTradeItem(entity.getIsTradeItem());

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
        setItemStoreMappingInfo(dto, entity);

        return dto;
    }

    private void setItemStoreMappingInfo(ItemFindByIdResponseDto dto, Item entity) {
        ItemStoreMapping itemStoreMapping = itemStoreMappingRepository.findByItemInfoId(entity.getId()).orElse(null);
        ItemStoreMappingFindByItemIdResponseDto mapper = new ItemStoreMappingFindByItemIdResponseDto();

        if (itemStoreMapping != null) {
            mapper.setId(itemStoreMapping.getId());
            mapper.setItemInfoId(itemStoreMapping.getItemInfoId());

            ProductType productType = productTypeRepository.findById(itemStoreMapping.getProdTypeId()).orElse(null);

            if (productType != null) {
                // Set product type info
                mapper.setProdTypeId(productType.getId());
                mapper.setProdTypeName(productType.getName());

                if (productType.getProductCategory() != null) {
                    // Set product category info
                    mapper.setCategoryId(productType.getProductCategory().getId());
                    mapper.setCategoryName(productType.getProductCategory().getName());

                }
            }
            setItemGroupInfo(mapper, itemStoreMapping);
        }

        dto.setMapper(mapper);
    }

    private void setItemGroupInfo(ItemStoreMappingFindByItemIdResponseDto mapper, ItemStoreMapping itemStoreMapping) {
        ItemSubGroup itemSubGroup = itemSubGroupRepository.findById(itemStoreMapping.getItmSubGrpId()).orElse(null);

        if (itemSubGroup != null) {
            mapper.setItmGrpSubId(itemSubGroup.getId());
            mapper.setItmGrpSubName(itemSubGroup.getItmSubGrpName());

            // Fetch item group and master group
            setItemMasterGroupInfo(mapper, itemSubGroup);
        }
    }

    private void setItemMasterGroupInfo(ItemStoreMappingFindByItemIdResponseDto mapper, ItemSubGroup itemSubGroup) {
        ItemGroup itemGroup = itemSubGroup.getItemGroup();

        if (itemGroup != null) {
            // Set item group info
            mapper.setItmGrpId(itemGroup.getId());
            mapper.setItmGrpName(itemGroup.getItmGrpName());

            ItemMasterGroup itemMasterGroup = itemGroup.getItemMasterGroup();

            if (itemMasterGroup != null) {
                // Set item master group info
                mapper.setItmMstrGrpId(itemMasterGroup.getId());
                mapper.setItmMstrGrpName(itemMasterGroup.getItmMstrGrpName());

                // Fetch and set product type info
                // setProductTypeInfo(mapper, itemMasterGroup);
            }
        }
    }

    // private void setProductTypeInfo(ItemStoreMappingFindByItemIdResponseDto
    // mapper, ItemMasterGroup itemMasterGroup) {

    // }

}
