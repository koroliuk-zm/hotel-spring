package com.dkoroliuk.hotel_spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dkoroliuk.hotel_spring.dto.RoomDTO;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.entity.RoomType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class RoomServiceIntegrationTest {
	@Autowired
	private RoomService service;
	
    @Test
    void testSaveRoom() {
        Room room = service.saveRoom(new RoomDTO(1L, 1, 1, 1, new RoomStatus(1, "free"), new RoomType(1, "standart"), "room 1"));
        assertEquals(1, room.getNumber());
        assertEquals(1, room.getSeatsAmount());
        assertEquals(1, room.getPerdayCost());
        assertEquals("room 1", room.getDescription());
    }
    
    @Test
    void testGetAllRooms() {
    	
    }

}
