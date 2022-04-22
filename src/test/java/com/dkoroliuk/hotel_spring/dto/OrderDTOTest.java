package com.dkoroliuk.hotel_spring.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.dkoroliuk.hotel_spring.entity.Order;
import com.dkoroliuk.hotel_spring.entity.OrderStatus;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.entity.RoomType;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.entity.UserRole;

class OrderDTOTest {
	@Test
	void testConstructorAndGetters() throws Exception {
		RoomStatus rs = new RoomStatus(1, "free");
		RoomType rt = new RoomType(1, "standart");
		UserRole ur = new UserRole(3, "user");
		OrderStatus os = new OrderStatus(1, "new");
		Room room = new Room(1L, 1, 1, 1, rs, rt, "description");
		User user = new User(100L, "name", "surname", "login", "password", "email", ur, true);
		OrderDTO order = new OrderDTO(1L, LocalDateTime.of(2022, 4, 20, 17, 0), LocalDate.of(2022, 4, 20), LocalDate.of(2022, 4, 20), 1, os, user, room);
		assertEquals(1L, order.getId());
		assertEquals(LocalDateTime.of(2022, 4, 20, 17, 0), order.getOrderDate());
		assertEquals(LocalDate.of(2022, 4, 20), order.getCheckInDate());
		assertEquals(LocalDate.of(2022, 4, 20), order.getCheckOutDate());
		assertEquals(1, order.getTotalCost());
		assertEquals(1, order.getOrderStatus().getId());
		assertEquals("new", order.getOrderStatus().getStatus());
		assertEquals(100L, order.getUser().getId());
		assertEquals("name", order.getUser().getName());
		assertEquals("surname", order.getUser().getSurname());
		assertEquals("login", order.getUser().getLogin());
		assertEquals("password", order.getUser().getPassword());
		assertEquals("email", order.getUser().getEmail());
		assertEquals(3, order.getUser().getUserRole().getId());
		assertEquals("user", order.getUser().getUserRole().getRole());
		assertTrue(order.getUser().isEnable());
		assertEquals(1L, order.getRoom().getId());
		assertEquals(1, order.getRoom().getNumber());
		assertEquals(1, order.getRoom().getSeatsAmount());
		assertEquals(1, order.getRoom().getPerdayCost());
		assertEquals(1, order.getRoom().getRoomStatus().getId());
		assertEquals("free", order.getRoom().getRoomStatus().getStatus());
		assertEquals(1, order.getRoom().getRoomType().getId());
		assertEquals("standart", order.getRoom().getRoomType().getType());
		assertEquals("description", order.getRoom().getDescription());
	}

	@Test
	void equalsHashcodeVerify() {
		RoomStatus rs = new RoomStatus(1, "free");
		RoomType rt = new RoomType(1, "standart");
		UserRole ur = new UserRole(3, "user");
		OrderStatus os = new OrderStatus(1, "new");
		Room room = new Room(1L, 1, 1, 1, rs, rt, "description");
		User user = new User(100L, "name", "surname", "login", "password", "email", ur, true);
		OrderDTO order1 = new OrderDTO(1L, LocalDateTime.of(2022, 4, 20, 17, 0), LocalDate.of(2022, 4, 20), LocalDate.of(2022, 4, 20), 1, os, user, room);
		OrderDTO order2 = new OrderDTO(1L, LocalDateTime.of(2022, 4, 20, 17, 0), LocalDate.of(2022, 4, 20), LocalDate.of(2022, 4, 20), 1, os, user, room);
		assertEquals(order1, order2);
		assertEquals(order1.hashCode(), order2.hashCode());
	}
}
