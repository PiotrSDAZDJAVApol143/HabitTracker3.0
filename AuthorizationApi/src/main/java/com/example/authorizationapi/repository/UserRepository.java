package com.example.authorizationapi.repository;


import com.example.authorizationapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByLogin(String login);
    @Query(nativeQuery = true, value = "SELECT * FROM APP_USER where USER_NAME=?1 and IS_LOCK=false and IS_ENABLE=true")
    Optional<User> findUserByLoginAndLockAndEnabled(String login);
    @Query(nativeQuery = true, value = "SELECT * FROM APP_USER where USER_NAME=?1 and IS_LOCK=false and IS_ENABLE=true and role='ADMIN'")
    Optional<User> findUserByLoginAndLockAndEnabledAndIsAdmin(String login);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUuid(String uuid);
}
