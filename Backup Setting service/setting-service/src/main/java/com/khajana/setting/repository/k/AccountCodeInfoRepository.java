package com.khajana.setting.repository.k;

import com.khajana.setting.entity.k.AccountCodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountCodeInfoRepository extends JpaRepository<AccountCodeInfo, Long> {
    Optional<AccountCodeInfo> findAccountCodeInfoById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deleteAccountCodeInfoById(Long id);
}
