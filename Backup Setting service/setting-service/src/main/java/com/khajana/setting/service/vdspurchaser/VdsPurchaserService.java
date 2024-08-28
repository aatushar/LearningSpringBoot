package com.khajana.setting.service.vdspurchaser;

import com.khajana.setting.dto.ApiResponse;
import com.khajana.setting.dto.vdspurchaser.VdsPurchaserChildRequestDto;
import com.khajana.setting.dto.vdspurchaser.VdsPurchaserRequestDto;
import com.khajana.setting.dto.vdspurchaser.VdsPurchaserResponseDto;
import com.khajana.setting.entity.User;
import com.khajana.setting.entity.companybranch.CompanyBranch;
import com.khajana.setting.entity.companybranch.CompanySupplier;
import com.khajana.setting.entity.vat.VatMonthInfo;
import com.khajana.setting.entity.vdspurchaser.VdsPurchaserChild;
import com.khajana.setting.entity.vdspurchaser.VdsPurchaserMaster;
import com.khajana.setting.exception.ResourceNotFoundException;
import com.khajana.setting.repository.UserCredentialRepository;
import com.khajana.setting.repository.companybranch.CompanyBranchRepository;
import com.khajana.setting.repository.companybranch.CompanySupplierRepository;
import com.khajana.setting.repository.vat.VatMonthInfoRepository;
import com.khajana.setting.repository.vdspurchaser.VdsPurchaserChildRepository;
import com.khajana.setting.repository.vdspurchaser.VdsPurchaserRepository;
import com.khajana.setting.security.CustomUserDetails;
import com.khajana.setting.utils.ConstantValue;
import com.khajana.setting.utils.ContextUser;
import com.khajana.setting.utils.DateUtil;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VdsPurchaserService {

    @Autowired
    VdsPurchaserRepository vdsPurchaserRepository;
    @Autowired
    UserCredentialRepository userCredentials;

    @Autowired
    VdsPurchaserChildRepository vdsPurchaserChildRepository;

    @Autowired
    CompanyBranchRepository companyBranchRepository;
    @Autowired
    VatMonthInfoRepository vatMonthInfoRepository;
    @Autowired
    CompanySupplierRepository companySupplierRepository;

    public SimplePage<VdsPurchaserResponseDto> findAllVdsPurchasers(Pageable pageable) {
        Page<VdsPurchaserMaster> page = vdsPurchaserRepository.findAll(pageable);
        return new SimplePage<>(page.getContent().stream().map(this::transferToDTO).collect(Collectors.toList()),
                page.getTotalElements(), pageable);
    }

    public VdsPurchaserResponseDto findVdsPurchaserById(Long id) {

        VdsPurchaserMaster newEntity = vdsPurchaserRepository.findVdsPurchaserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "Id", id));

        return transferToDTO(newEntity);
    }

    public ApiResponse addVdsPurchaser(VdsPurchaserRequestDto dto) {
        try {
            VdsPurchaserMaster newEntity = vdsPurchaserRepository.save(transferToEntity(0L, dto));
            return new ApiResponse(200, "ok", transferToDTO(newEntity));
        } catch (Exception e) {
            return new ApiResponse(500, e.getMessage(), "error");
        }
    }

    public VdsPurchaserResponseDto updateVdsPurchaser(Long id, VdsPurchaserRequestDto dto) {
        VdsPurchaserMaster newEntity = vdsPurchaserRepository.save(transferToEntity(id, dto));
        return transferToDTO(newEntity);
    }

    public List<VdsPurchaserResponseDto> getDropDown() {
        List<VdsPurchaserMaster> page = vdsPurchaserRepository.findAll();
        return page.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public List<VdsPurchaserResponseDto> getUnpaidPaymentHistory() {
        // VdsPurchaserMaster where tc_master_id = null && is_paid = false
        List<VdsPurchaserMaster> unpaidRecords = vdsPurchaserRepository.findByIsPaidFalse();
        return unpaidRecords.stream().map(this::transferToDTO).collect(Collectors.toList());
    }

    public void deleteVdsPurchaser(Long id) {
        vdsPurchaserRepository.deleteVdsPurchaserById(id);
    }

    private VdsPurchaserMaster transferToEntity(Long id, VdsPurchaserRequestDto dto) {
        CustomUserDetails userData = ContextUser.getLoginUserData();
        VdsPurchaserMaster vdsPurchaser = new VdsPurchaserMaster();
        vdsPurchaser.setBranchId(dto.getBranchId());
        vdsPurchaser.setTransactionDate(dto.getTransactionDate());
        vdsPurchaser.setVmId(dto.getVmId());
        vdsPurchaser.setPublishedDate(dto.getPublishedDate());
        vdsPurchaser.setSupplierId(dto.getSupplierId());
        vdsPurchaser.setTcMasterId(dto.getTcMasterId());
        vdsPurchaser.setTotalRecvAmtWotaxLocalCurr(dto.getTotalRecvAmtWotaxLocalCurr());
        vdsPurchaser.setTotalVatAmount(dto.getTotalVatAmount());
        vdsPurchaser.setTotalDeductedVatAmount(dto.getTotalDeductedVatAmount());
        vdsPurchaser.setTotalVdsPaidAmount(dto.getTotalVdsPaidAmount());
        vdsPurchaser.setIsPaid(true);

        vdsPurchaser.setCreatedAt(new Date());
        vdsPurchaser.setCreatedBy(userData.getId());

        VdsPurchaserMaster insertedVdsPurchaserMaster = vdsPurchaserRepository.save(vdsPurchaser);
        vdsPurchaser.setCertificateNo("VDSPURCHASE-" + insertedVdsPurchaserMaster.getId());
        vdsPurchaserRepository.save(vdsPurchaser);

        if (!dto.getVdspurchasechild().isEmpty()) {
            for (VdsPurchaserChildRequestDto vdsPurchaserChildRequestDto : dto.getVdspurchasechild()) {
                VdsPurchaserChild vdsPurchaserChild = new VdsPurchaserChild();
                vdsPurchaserChild.setVdsPurchaserMasterId(insertedVdsPurchaserMaster.getId());
                vdsPurchaserChild.setReceiveMasterId(vdsPurchaserChildRequestDto.getReceiveMasterId());
                vdsPurchaserChild.setRecvAmtWotaxLocalCurr(vdsPurchaserChildRequestDto.getRecvAmtWotaxLocalCurr());
                vdsPurchaserChild.setVatAmount(vdsPurchaserChildRequestDto.getVatAmount());
                vdsPurchaserChild.setDeductedVatAmount(vdsPurchaserChildRequestDto.getDeductedVatAmount());
                vdsPurchaserChild.setCreatedAt(new Date());
                vdsPurchaserChild.setCreatedBy(userData.getId());
                vdsPurchaserChildRepository.save(vdsPurchaserChild);
            }
        }

        return vdsPurchaser;

    }

    private VdsPurchaserResponseDto transferToDTO(VdsPurchaserMaster entity) {
        VdsPurchaserResponseDto dto = new VdsPurchaserResponseDto();
        dto.setId(entity.getId());
        dto.setBranchId(entity.getBranchId());
        if (entity.getBranchId() != null) {
            CompanyBranch companyBranch = companyBranchRepository.findById(entity.getBranchId()).orElse(null);
            if (companyBranch != null && companyBranch.getBranchUnitName() != null) {
                dto.setBranchName(companyBranch.getBranchUnitName());
                dto.setBranchNameBn(companyBranch.getBranchUnitNameBn());
            }
        }
        dto.setTransactionDate(
                DateUtil.convertDateToString(entity.getTransactionDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setVmId(entity.getVmId());
        if (entity.getVmId() != null) {
            VatMonthInfo vatMonthInfo = vatMonthInfoRepository.findById(entity.getVmId()).orElse(null);
            if (vatMonthInfo != null && vatMonthInfo.getVmInfo() != null) {
                dto.setVmName(vatMonthInfo.getVmInfo());
            }
        }
        dto.setCertificateNo(entity.getCertificateNo());
        dto.setPublishedDate(
                DateUtil.convertDateToString(entity.getPublishedDate(), ConstantValue.OUT_GOING_DATE_PATTERN));
        dto.setSupplierId(entity.getSupplierId());
        if (entity.getSupplierId() != null) {
            CompanySupplier companySupplier = companySupplierRepository.findById(entity.getSupplierId()).orElse(null);
            if (companySupplier != null && companySupplier.getSupplierName() != null) {
                dto.setSupplierName(companySupplier.getSupplierName());
            }
        }
        dto.setTcMasterId(entity.getTcMasterId());
        dto.setTotalRecvAmtWotaxLocalCurr(entity.getTotalRecvAmtWotaxLocalCurr());
        dto.setTotalVatAmount(entity.getTotalVatAmount());
        dto.setTotalDeductedVatAmount(entity.getTotalDeductedVatAmount());
        dto.setTotalVdsPaidAmount(entity.getTotalVdsPaidAmount());
        dto.setIsPaid(entity.getIsPaid());
        dto.setCreatedAt(DateUtil.convertDateToString(entity.getCreatedAt(), ConstantValue.OUT_GOING_DATE_PATTERN));
        if (entity.getCreatedBy() != null) {
            User createdByUser = userCredentials.findById(entity.getCreatedBy()).orElse(null);
            dto.setCreatedBy(createdByUser != null ? createdByUser.getName() : null);
        }
        return dto;
    }
}
