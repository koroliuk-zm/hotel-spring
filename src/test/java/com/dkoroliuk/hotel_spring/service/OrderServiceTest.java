package com.dkoroliuk.hotel_spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dkoroliuk.hotel_spring.entity.Order;
import com.dkoroliuk.hotel_spring.entity.OrderStatus;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.entity.RoomType;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.entity.UserRole;
import com.dkoroliuk.hotel_spring.repository.OrderRepo;
import com.dkoroliuk.hotel_spring.repository.RequestRepo;
import com.dkoroliuk.hotel_spring.repository.RoomRepo;
import com.dkoroliuk.hotel_spring.service.impl.OrderServiceImpl;
import com.dkoroliuk.hotel_spring.util.DTOHelper;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
	private static final Order ORDER = new Order(1L, LocalDateTime.of(2022, 4, 22, 15, 52),
			LocalDate.of(2022, 4, 22), LocalDate.of(2022, 4, 22), 100, new OrderStatus(1, "new"), 
			new User(1L, "name", "surname", "login", "password", "email", new UserRole(3, "user"), true), 
			new Room(1, 1, 1, 100, new RoomStatus(1, "free"), new RoomType(1, "standart" ), "description"));
	
	private static final Room ROOM = new Room(1, 1, 1, 100, new RoomStatus(1, "free"), new RoomType(1, "standart" ), "description");
	
	@Mock
	private RoomRepo roomRepoMock;
	@Mock
	private OrderRepo orderRepoMock;
	@Mock
	private RequestRepo requestRepoMock;
	@InjectMocks
	private OrderServiceImpl service;
	@Mock
	private Order orderMock;
	
	@Test
	void testFindUsersOngoingOrders() {
		when(orderRepoMock.findAllByUserIdAndOrderStatusIdEquals(1L, 2)).thenReturn(Arrays.asList(orderMock));
		assertEquals(DTOHelper.toDTO(orderMock), service.findUsersOngoingOrders(1L).get(0));
	}
	
	@Test
	void testFindUsersProcessedOrders() {
		when(orderRepoMock.findAllByUserIdAndOrderStatusIdEquals(1L, 3)).thenReturn(Arrays.asList(orderMock));
		assertEquals(DTOHelper.toDTO(orderMock), service.findUsersProcessedOrders(1L).get(0));
	}
	
	
	@Test
	void testSaveOrder() {
		ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
		when(roomRepoMock.getById(anyLong())).thenReturn(ROOM);
		service.saveRoomOrder(DTOHelper.toDTO(ORDER));
		verify(orderRepoMock).save(orderCaptor.capture());
		assertEquals(100, orderCaptor.getValue().getTotalCost());
		assertEquals(LocalDateTime.of(2022, 4, 22, 15, 52), orderCaptor.getValue().getOrderDate());
		assertEquals(LocalDate.of(2022, 4, 22), orderCaptor.getValue().getCheckInDate());
		assertEquals(LocalDate.of(2022, 4, 22), orderCaptor.getValue().getCheckOutDate());
		assertEquals(new RoomStatus(1, "free"), orderCaptor.getValue().getRoom().getRoomStatus());
		assertEquals(new RoomType(1, "standart"), orderCaptor.getValue().getRoom().getRoomType());
	}
	
	@Test
	void testCreateOrderOnRequest() {
		ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
		when(roomRepoMock.getRoomByNumber(anyInt())).thenReturn(ROOM);
		when(roomRepoMock.getById(anyLong())).thenReturn(ROOM);
        service.createOrderOnRequest(DTOHelper.toDTO(ORDER));
        verify(orderRepoMock).save(orderCaptor.capture());
		assertEquals(100, orderCaptor.getValue().getTotalCost());
		assertEquals(LocalDateTime.of(2022, 4, 22, 15, 52), orderCaptor.getValue().getOrderDate());
		assertEquals(LocalDate.of(2022, 4, 22), orderCaptor.getValue().getCheckInDate());
		assertEquals(LocalDate.of(2022, 4, 22), orderCaptor.getValue().getCheckOutDate());
		assertEquals(new RoomStatus(1, "free"), orderCaptor.getValue().getRoom().getRoomStatus());
		assertEquals(new RoomType(1, "standart"), orderCaptor.getValue().getRoom().getRoomType());
	}
	
	@Test
	void testDeleteOrderById() {
		service.deleteOrderById(anyLong());
		verify(orderRepoMock).deleteById(anyLong());
	}
	
	@Test
	void testPayOrder() {
		service.payOrder(1L);
		verify(orderRepoMock).changeOrderStatus(1L, 3);
	}
	
	@Test
	void testGetRoomIdByOrder() {
		when(orderRepoMock.getById(anyLong())).thenReturn(ORDER);
		assertEquals(1, service.getRoomIdByOrder(ORDER.getId()));
	}
	
	@Test
	void testSetOrdersExpiredWhenIsNotPaidMoreThanTwoDays() {
		when(orderRepoMock.findAll()).thenReturn(Arrays.asList(ORDER));
		when(roomRepoMock.getById(anyLong())).thenReturn(ROOM);
		service.setOrdersExpiredWhenIsNotPaidMoreThanTwoDays();
		verify(orderRepoMock).save(any(Order.class));
		verify(roomRepoMock).getById(anyLong());
		verify(roomRepoMock).save(any(Room.class));
	}
	
	@Test
	void testCloseOrder() {
		Order order = new Order(1L, LocalDateTime.of(2022, 4, 21, 15, 52),
				LocalDate.of(2022, 4, 21), LocalDate.of(2022, 4, 21), 100, new OrderStatus(3, "paid"), 
				new User(1L, "name", "surname", "login", "password", "email", new UserRole(3, "user"), true), 
				new Room(1, 1, 1, 100, new RoomStatus(1, "free"), new RoomType(1, "standart" ), "description"));
		System.out.println(order);
		when(orderRepoMock.findAll()).thenReturn(Arrays.asList(order));
		when(roomRepoMock.getById(anyLong())).thenReturn(ROOM);
		service.closeOrder();
		verify(orderRepoMock).save(any(Order.class));
		verify(roomRepoMock).getById(anyLong());
		verify(roomRepoMock).save(any(Room.class));
	}
}
