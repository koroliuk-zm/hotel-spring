package com.dkoroliuk.hotel_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dkoroliuk.hotel_spring.entity.Request;

@Repository
public interface RequestRepo extends JpaRepository<Request, Long> {

	List<Request> findAllByUserId(long id);

}
