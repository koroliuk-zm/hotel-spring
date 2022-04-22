package com.dkoroliuk.hotel_spring.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class RequestTest {
	@Test
	void testConstructorAndGetters() throws Exception {
		RoomType rt = new RoomType(1, "standart");
		UserRole ur = new UserRole(3, "user");
		User user = new User(100L, "name", "surname", "login", "password", "email", ur, true);
		Request request = new Request(1L, LocalDateTime.of(2022, 4, 20, 17, 0), LocalDate.of(2022, 4, 20), LocalDate.of(2022, 4, 20), 1, user, rt);
		assertEquals(1L, request.getId());
		assertEquals(LocalDateTime.of(2022, 4, 20, 17, 0), request.getRequestDate());
		assertEquals(LocalDate.of(2022, 4, 20), request.getCheckInDate());
		assertEquals(LocalDate.of(2022, 4, 20), request.getCheckOutDate());
		assertEquals(1, request.getSeatsNumber());
		assertEquals(1, request.getRoomType().getId());
		assertEquals("standart", request.getRoomType().getType());
		assertEquals(100L, request.getUser().getId());
		assertEquals("name", request.getUser().getName());
		assertEquals("surname", request.getUser().getSurname());
		assertEquals("login", request.getUser().getLogin());
		assertEquals("password", request.getUser().getPassword());
		assertEquals("email", request.getUser().getEmail());
		assertEquals(3, request.getUser().getUserRole().getId());
		assertEquals("user", request.getUser().getUserRole().getRole());
		assertTrue(request.getUser().isEnable());
	}

	@Test
	void equalsHashcodeVerify() {
		RoomType rt = new RoomType(1, "standart");
		UserRole ur = new UserRole(3, "user");
		User user = new User(100L, "name", "surname", "login", "password", "email", ur, true);
		Request request1 = new Request(1L, LocalDateTime.of(2022, 4, 20, 17, 0), LocalDate.of(2022, 4, 20), LocalDate.of(2022, 4, 20), 1, user, rt);
		Request request2 = new Request(1L, LocalDateTime.of(2022, 4, 20, 17, 0), LocalDate.of(2022, 4, 20), LocalDate.of(2022, 4, 20), 1, user, rt);
		assertEquals(request1, request2);
		assertEquals(request1.hashCode(), request2.hashCode());
	}
}
