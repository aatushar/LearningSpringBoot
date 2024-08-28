package com.khajana.setting.repository.transactiontype;

import com.khajana.setting.entity.transactiontype.TransAccountItemInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransAccountItemInfoRepo extends JpaRepository<TransAccountItemInfo, Long> {
    Optional<TransAccountItemInfo> findTransAccountItemById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
