package com.dkoroliuk.hotel_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dkoroliuk.hotel_spring.entity.UserRole;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
}
