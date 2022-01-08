package com.dkoroliuk.hotel_spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dkoroliuk.hotel_spring.entity.User;



@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin(String login);
}
