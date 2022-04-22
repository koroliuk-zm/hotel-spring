package com.dkoroliuk.hotel_spring.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RoomTest {
	@Test
	void testConstructorAndGetters() throws Exception {
		RoomStatus rs = new RoomStatus(1, "free");
		RoomType rt = new RoomType(1, "standart");
		Room room = new Room(1L, 1, 1, 1, rs, rt, "description");
		assertEquals(1L, room.getId());
		assertEquals(1, room.getNumber());
		assertEquals(1, room.getSeatsAmount());
		assertEquals(1, room.getPerdayCost());
		assertEquals(1, room.getRoomStatus().getId());
		assertEquals("free", room.getRoomStatus().getStatus());
		assertEquals(1, room.getRoomType().getId());
		assertEquals("standart", room.getRoomType().getType());
		assertEquals("description", room.getDescription());
	}

	@Test
	void equalsHashcodeVerify() {
		RoomStatus rs = new RoomStatus(1, "free");
		RoomType rt = new RoomType(1, "standart");
		Room room1 = new Room(1L, 1, 1, 1, rs, rt, "description");
		Room room2 = new Room(1L, 1, 1, 1, rs, rt, "description");

		assertEquals(room1, room2);
		assertEquals(room1.hashCode(), room2.hashCode());
	}
}
