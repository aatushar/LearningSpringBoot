package com.khajana.setting.repository.ordermanagement.utilizationdeclaration;

import com.khajana.setting.entity.ordermanagement.utilizationdeclaration.UtilizationDeclarationChild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilizationDeclarationChildRepository extends JpaRepository<UtilizationDeclarationChild, Long> {
    Optional<UtilizationDeclarationChild> findUtilizationDeclarationChildById(Long id);

    void deleteUtilizationDeclarationChildById(Long id);
}
