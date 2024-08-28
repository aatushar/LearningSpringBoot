package com.khajana.setting.service.issuemaster;

import com.khajana.setting.dto.issuemaster.IssueMasterDto;
import com.khajana.setting.repository.issuemaster.IssueMasterRepo;
import com.khajana.setting.utils.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IssueMasterService {
    @Autowired
    IssueMasterRepo issueMasterRepo;

    public SimplePage<IssueMasterDto> findAllIssues(Pageable pageable) {
        Page<IssueMasterDto> page = issueMasterRepo.getJoinInformationIssue(pageable);
        return new SimplePage<>(page.getContent(), page.getTotalElements(), pageable);
    }
}
