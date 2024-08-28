package com.khajana.setting.service.issuemaster;

import com.khajana.setting.entity.issuemaster.LocalSellTest;
import com.khajana.setting.repository.issuemaster.LocalSellTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalSellTestService {
    @Autowired
    LocalSellTestRepository localSellTestRepository;

    public List<LocalSellTest> executeStoredProcedure() {
        List<LocalSellTest> result = localSellTestRepository.executeStoredProcedur();
        return result;
    }
}
