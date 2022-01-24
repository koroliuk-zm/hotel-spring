package com.dkoroliuk.hotel_spring.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.dkoroliuk.hotel_spring.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE o.user.id = ?1 AND o.orderStatus.id = 2 AND o.room IS NOT NULL")
	List<Order> getUsersOngoingOrders(long userId);
	
	@Query("SELECT o FROM Order o WHERE o.user.id = ?1 AND o.orderStatus.id >= 3 AND o.room IS NOT NULL")
	List<Order> getUsersProcessedOrders(long userId);

	@Query("SELECT o FROM Order o WHERE o.orderStatus.id = 1")
	Page<Order> findNewRoomOrdersPageable(Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("UPDATE Order o SET o.orderStatus.id=?2 WHERE o.id = ?1")
	void changeOrderStatus(Long orderId, int newOrderStatus);

	

}
