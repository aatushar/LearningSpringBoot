package com.khajana.setting.repository.legaldocument;

import com.khajana.setting.entity.legaldocument.LegalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LegalDocumentRepository extends JpaRepository<LegalDocument, Long> {
    Optional<LegalDocument> findLegalDocumentById(Long id);

    void deleteLegalDocumentById(Long id);
}
