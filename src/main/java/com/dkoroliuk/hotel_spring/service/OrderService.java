package com.dkoroliuk.hotel_spring.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dkoroliuk.hotel_spring.dto.OrderDTO;
import com.dkoroliuk.hotel_spring.entity.Order;

public interface OrderService {

	List<OrderDTO> findUsersOngoingOrders(long userId);

	OrderDTO saveRoomOrder(OrderDTO roomOrderDTO);


	Order updateOrder(Long id, OrderDTO orderDTO);

	void deleteOrderById(Long id);
	
	Long getRoomIdByOrder(Long id);

	void payOrder(Long id);

	List<OrderDTO> findUsersProcessedOrders(long userById);
	
	@Transactional
	void setOrdersExpiredWhenIsNotPaidMoreThanTwoDays();
	@Transactional
	void closeOrder();
}
