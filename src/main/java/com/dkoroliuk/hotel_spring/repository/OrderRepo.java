package com.dkoroliuk.hotel_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dkoroliuk.hotel_spring.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

	List<Order> findAllByUserIdAndOrderStatusIdEquals(long userId, int orderStatusId);

	@Transactional
	@Modifying
	@Query("UPDATE Order o SET o.orderStatus.id=?2 WHERE o.id = ?1")
	void changeOrderStatus(Long orderId, int newOrderStatus);

}
