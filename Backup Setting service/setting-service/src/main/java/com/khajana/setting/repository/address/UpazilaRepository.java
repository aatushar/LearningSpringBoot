package com.khajana.setting.repository.address;

import com.khajana.setting.entity.address.Upazila;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UpazilaRepository extends JpaRepository<Upazila, Long> {
    Optional<Upazila> findUpazilaById(Long id);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    void deleteUpazilaById(Long id);
}
