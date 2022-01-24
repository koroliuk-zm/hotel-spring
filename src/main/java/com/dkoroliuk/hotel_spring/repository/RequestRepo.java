package com.dkoroliuk.hotel_spring.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dkoroliuk.hotel_spring.entity.Request;

public interface RequestRepo extends JpaRepository<Request, Long> {

	@Query("SELECT r FROM Request r WHERE r.user.id = ?1 AND r.isProceed = 0")
	List<Request> findUnhandledRequests(long id);

	@Query("SELECT r FROM Request r WHERE r.isProceed = 0")
	Page<Request> findNewRequestPageable(Pageable pageRequest);

}
