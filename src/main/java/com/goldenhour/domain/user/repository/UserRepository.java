package com.goldenhour.domain.user.repository;

import com.goldenhour.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailOrUsername(String email, String username);

    Optional<User> findByUsername(String username);

}
