package com.khajana.setting.repository.debitnotefor;

import com.khajana.setting.entity.debitnotefor.DebitNoteFor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DebitNoteForRepository extends JpaRepository<DebitNoteFor, Long> {
    Optional<DebitNoteFor> findDebitNoteForById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deleteDebitNoteForById(Long id);
}
