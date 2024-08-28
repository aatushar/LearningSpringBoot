package com.khajana.setting.repository.documenttype;

import com.khajana.setting.entity.documenttype.DocumentTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentTypesRepository extends JpaRepository<DocumentTypesEntity, Long> {
    Optional<DocumentTypesEntity> findDocumentTypesById(Long id);

    boolean existsByDocType(String name);

    boolean existsByDocTypeAndIdNot(String name, Long id);
}
