package com.sparta.user.infastructure.repository;

import com.sparta.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface JpaUserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where (u.username = :username or u.email = :email or u.slackName = :slackName)")
    Optional<User> findDulicate(@Param("username") String username,
                                @Param("email") String email,
                                @Param("slackName") String slackName);

    Optional<User> findByUsername(@Param("username") String username);
}
