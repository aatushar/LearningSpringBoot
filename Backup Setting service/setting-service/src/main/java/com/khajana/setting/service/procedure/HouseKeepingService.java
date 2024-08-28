package com.khajana.setting.service.procedure;

import com.khajana.setting.entity.procedure.HouseKeeping;
import com.khajana.setting.repository.procedure.HouseKeepingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseKeepingService {

    @Autowired
    private HouseKeepingRepository houseKeepingRepository;

    public List<HouseKeeping> executeStoredProcedure() {
        List<HouseKeeping> result = houseKeepingRepository.executeStoredProcedure();
        return result;
    }

    public List<HouseKeeping> customerDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.customerDropDown();
        return result;
    }

    public List<HouseKeeping> companyDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.companyDropDown();
        return result;
    }

    public List<HouseKeeping> companyStoreDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.companyStoreDropDown();
        return result;
    }

    public List<HouseKeeping> vatStructureDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.vatStructureDropDown();
        return result;
    }

    public List<HouseKeeping> userCompanyStoreMappingDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.userCompanyStoreMappingDropDown();
        return result;
    }

    public List<HouseKeeping> vatMonthDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.vatMonthDropDown();
        return result;
    }

    public List<HouseKeeping> companyListDropDown() {
        List<HouseKeeping> result = houseKeepingRepository.companyListDropDown();
        return result;
    }

    public List<HouseKeeping> customerHavingIssue() {
        List<HouseKeeping> result = houseKeepingRepository.customerHavingIssue();
        return result;
    }

    public List<HouseKeeping> fiscalYearVatMonthFromInputDate(java.sql.Date inputDate) {
        List<HouseKeeping> result = houseKeepingRepository.fiscalYearVatMonthFromInputDate(inputDate);
        return result;
    }
}
