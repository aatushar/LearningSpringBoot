package com.khajana.setting.repository.ordermanagement.utilizationdeclaration;

import com.khajana.setting.entity.ordermanagement.utilizationdeclaration.UtilizationDeclarationMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilizationDeclarationMasterRepository extends JpaRepository<UtilizationDeclarationMaster, Long> {
    Optional<UtilizationDeclarationMaster> findUtilizationDeclarationMasterById(Long id);


    boolean existsByUdRegisterNo(String udRegisterNo);

    void deleteUtilizationDeclarationMasterById(Long id);
}
