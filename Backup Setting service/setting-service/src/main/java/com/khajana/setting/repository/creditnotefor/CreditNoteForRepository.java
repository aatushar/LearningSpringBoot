package com.khajana.setting.repository.creditnotefor;

import com.khajana.setting.entity.creditnotefor.CreditNoteFor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditNoteForRepository extends JpaRepository<CreditNoteFor, Long> {
    Optional<CreditNoteFor> findCreditNoteForById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deleteCreditNoteForById(Long id);
}
