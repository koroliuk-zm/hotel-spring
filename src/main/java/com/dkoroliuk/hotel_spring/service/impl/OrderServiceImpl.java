package com.dkoroliuk.hotel_spring.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dkoroliuk.hotel_spring.dto.OrderDTO;
import com.dkoroliuk.hotel_spring.entity.Order;
import com.dkoroliuk.hotel_spring.entity.OrderStatus;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.repository.OrderRepo;
import com.dkoroliuk.hotel_spring.repository.RequestRepo;
import com.dkoroliuk.hotel_spring.repository.RoomRepo;
import com.dkoroliuk.hotel_spring.service.OrderService;
import com.dkoroliuk.hotel_spring.util.DTOHelper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class OrderServiceImpl implements OrderService {
	OrderRepo orderRepo;
	RoomRepo roomRepo;
	RequestRepo requestRepo;


	@Override
	public List<OrderDTO> findUsersOngoingOrders(long userId) {
		return DTOHelper.orderListToDTO(orderRepo.getUsersOngoingOrders(userId));
	}
	
	@Override
	public List<OrderDTO> findUsersProcessedOrders(long userId) {
		return DTOHelper.orderListToDTO(orderRepo.getUsersProcessedOrders(userId));
	}

	@Override
	@Transactional
	public OrderDTO saveRoomOrder(OrderDTO roomOrderDTO) {
		Order order = new Order();
		order.setOrderDate(roomOrderDTO.getOrderDate());
		order.setCheckInDate(roomOrderDTO.getCheckInDate());
		order.setCheckOutDate(roomOrderDTO.getCheckOutDate());
		order.setOrderStatus(roomOrderDTO.getOrderStatus());
		order.setUser(roomOrderDTO.getUser());
		order.setRoom(roomOrderDTO.getRoom());
		roomRepo.changeRoomStatus(order.getRoom().getId(), 2);
		Room room = roomRepo.getById(order.getRoom().getId());
		order.setTotalCost((int) ((ChronoUnit.DAYS.between(roomOrderDTO.getCheckInDate(), roomOrderDTO.getCheckOutDate())+1)*room.getPerdayCost()));
		return DTOHelper.toDTO(orderRepo.save(order));
	}

	@Override
	@Transactional
	public Order updateOrder(Long id, OrderDTO roomOrderDTO) {
		Order order = new Order();
		order.setOrderDate(roomOrderDTO.getOrderDate());
		order.setCheckInDate(roomOrderDTO.getCheckInDate());
		order.setCheckOutDate(roomOrderDTO.getCheckOutDate());
		order.setOrderStatus(roomOrderDTO.getOrderStatus());
		order.setUser(roomOrderDTO.getUser());
		order.setRoom(roomRepo.getRoomByNumber(roomOrderDTO.getRoom().getNumber()));
		roomRepo.changeRoomStatus(order.getRoom().getId(), 2);
		Room room = roomRepo.getById(order.getRoom().getId());
		order.setTotalCost((int) ((ChronoUnit.DAYS.between(roomOrderDTO.getCheckInDate(), roomOrderDTO.getCheckOutDate())+1)*room.getPerdayCost()));
		return orderRepo.save(order);
	}
	
	
	@Override
	public void deleteOrderById(Long id) {
		orderRepo.deleteById(id);
	}

	@Override
	public Long getRoomIdByOrder(Long id) {
		Order order = orderRepo.getById(id);
		return  order.getRoom().getId();
	}

	@Override
	public void payOrder(Long id) {
		orderRepo.changeOrderStatus(id, 3);
	}


	@Scheduled(fixedDelay = 10000, initialDelay = 1000)
	@Override
	public void setOrdersExpiredWhenIsNotPaidMoreThanTwoDays() {
		List<Order> orders = orderRepo.findAll();
		for(Order order:orders) {
			if(ChronoUnit.MINUTES.between(order.getOrderDate(),LocalDateTime.now())>1&&order.getOrderStatus().getId()<3) {
				Room room = roomRepo.getById(order.getRoom().getId());
				room.setRoomStatus(new RoomStatus());
				room.getRoomStatus().setId(1);
				roomRepo.save(room);
				order.setOrderStatus(new OrderStatus());
				order.getOrderStatus().setId(4);
				orderRepo.save(order);
			}
		}
	}


	@Scheduled(fixedDelay = 10000, initialDelay = 1000)
	@Override
	public void closeOrder() {
		List<Order> orders = orderRepo.findAll();
		for(Order order:orders) {
			if(LocalDate.now().isAfter(order.getCheckOutDate())&&order.getOrderStatus().getId()==3) {
				Room room = roomRepo.getById(order.getRoom().getId());
				room.setRoomStatus(new RoomStatus());
				room.getRoomStatus().setId(1);
				roomRepo.save(room);
				order.setOrderStatus(new OrderStatus());
				order.getOrderStatus().setId(5);
				orderRepo.save(order);
			}
		}
	}
}
