package com.dkoroliuk.hotel_spring.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.entity.RoomType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomRepoTest {
@Autowired
private RoomRepo roomRepo;

@BeforeEach
void setUp() {
	Room savedRoom  = roomRepo.save(new Room(100L, 1000, 100, 100, new RoomStatus(2, "booked"), new RoomType(1, "standart"), "test room"));
}
@Test
void testGetRoomByNumber() {
	Room room = roomRepo.getRoomByNumber(1000);
	assertEquals("test room", room.getDescription());
}

}
