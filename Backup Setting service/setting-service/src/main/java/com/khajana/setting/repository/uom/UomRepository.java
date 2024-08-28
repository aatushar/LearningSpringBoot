package com.khajana.setting.repository.uom;

import com.khajana.setting.dto.uom.UomResponseDto;
import com.khajana.setting.entity.uom.Uom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UomRepository extends JpaRepository<Uom, Long> {
    Optional<Uom> findUomById(Long id);

    @Query("SELECT new com.khajana.setting.dto.uom.UomResponseDto("
            + "t.id,t.uomSetId,j.uomSet,j.uomSetDesc,j.localUomSetDesc, t.uomShortCode,t.uomDesc,t.localDesc,t.relativeFactor,t.fractionAllow,"
            + "t.isActive,t.createdAt,t.createdBy) " + "FROM Uom t JOIN t.uomSet j")
    public Page<UomResponseDto> getJoinInformation(Pageable pageable);

    boolean existsByUomShortCode(String uomShortCode);

    boolean existsByUomShortCodeAndIdNot(String uomShortCode, Long id);
    /*
     * @Query("SELECT new com.khajana.setting.dto.uom.UomResponseDto(" +
     * "t.id,t.uomSetId,j.uomSet,j.uomSetDesc,j.localUomSetDesc, t.uomShortCode,t.uomDesc,t.localDesc,t.relativeFactor,t.fractionAllow,"
     * + "t.isActive,t.createdAt,t.createdBy) " +
     * "FROM Uom t JOIN t.uomSet j WHERE t.id = ?1") public UomResponseDto
     * findUomById(Long id);
     */

}
