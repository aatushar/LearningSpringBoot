package com.khajana.setting.repository.transactiontype;

import com.khajana.setting.entity.transactiontype.LcFor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LcForRepository extends JpaRepository<LcFor, Long> {
    boolean existsByDescription(String description);

    boolean existsByDescriptionAndIdNot(String description, Long id);

    Optional<LcFor> findLcForById(Long id);
}
