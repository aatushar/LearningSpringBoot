package com.khajana.setting.repository.issue;

import com.khajana.setting.entity.issue.IssueChild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WastageManagementChildRepository extends JpaRepository<IssueChild, Long> {
    Optional<IssueChild> findIssueChildById(Long id);


    void deleteIssueChildById(Long id);
}
