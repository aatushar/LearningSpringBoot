package com.khajana.setting.repository.user;

import com.khajana.setting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);

    void deleteUserById(Long id);
}
