package com.khajana.setting.repository.ioc;

import com.khajana.setting.entity.ioc.Ioc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IocRepository extends JpaRepository<Ioc, Long> {
    Optional<Ioc> findIocById(Long id);

    void deleteIocById(Long id);
}
