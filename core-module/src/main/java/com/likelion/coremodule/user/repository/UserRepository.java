package com.likelion.coremodule.user.repository;

import com.likelion.coremodule.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySubId(String subId);

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    void deleteByName(String name);
}
