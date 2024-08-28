package com.khajana.setting.repository.language;

import com.khajana.setting.entity.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findLanguageById(Long id);

    boolean existsByName(String trnsTypeName);

    boolean existsByNameAndIdNot(String trnsTypeName, Long id);

    void deleteLanguageById(Long id);
}
