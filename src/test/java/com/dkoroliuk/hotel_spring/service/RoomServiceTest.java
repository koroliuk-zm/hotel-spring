package com.dkoroliuk.hotel_spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dkoroliuk.hotel_spring.dto.RoomDTO;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.entity.RoomType;
import com.dkoroliuk.hotel_spring.repository.RoomRepo;
import com.dkoroliuk.hotel_spring.service.impl.RoomServiceImpl;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

	private static final RoomDTO ROOM_DTO = new RoomDTO(0L, 1, 1, 1, new RoomStatus(1, "free"),
			new RoomType(1, "standart"), "room 1");
	@Mock
	private RoomRepo roomRepoMock;
	@InjectMocks
	private RoomServiceImpl service;
	@Mock
	private Room roomMock;

	@Test
	void testGetAllRooms() {
		when(roomRepoMock.findAll()).thenReturn(Arrays.asList(roomMock));
		assertEquals(roomMock, service.getAllRooms().get(0));
	}

	@Test
	void testFindRoomById() {
		service.findRoomById(anyLong());
		verify(roomRepoMock).getById(anyLong());
	}

	@Test
	void testSaveRoom() {
		ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
		service.saveRoom(ROOM_DTO);
		verify(roomRepoMock).save(roomCaptor.capture());
		assertEquals(1, roomCaptor.getValue().getNumber());
		assertEquals(1, roomCaptor.getValue().getPerdayCost());
		assertEquals("room 1", roomCaptor.getValue().getDescription());
		assertEquals(new RoomStatus(1, "free"), roomCaptor.getValue().getRoomStatus());
		assertEquals(new RoomType(1, "standart"), roomCaptor.getValue().getRoomType());
	}
	
	@Test
	void testUpdateRoom() {
		when(roomRepoMock.getById(anyLong())).thenReturn(roomMock);
        service.updateRoom(anyLong(), ROOM_DTO);
        verify(roomRepoMock).save(any(Room.class));
        verify(roomMock).setNumber(1);
        verify(roomMock).setPerdayCost(1);
	}

	@Test
	void testGetAllRoomsPageable() {
		Pageable pageable = mock(Pageable.class);
		Page<Room> page = mock(Page.class);
		when(roomRepoMock.findAll(pageable)).thenReturn(page);
		assertEquals(page, service.getAllRoomsPageable(pageable));
	}

	@Test
	void testGetAllFreeRoomsPageable() {
		Pageable pageable = mock(Pageable.class);
		Page<Room> page = mock(Page.class);
		when(roomRepoMock.findAllByRoomStatusId(1, pageable)).thenReturn(page);
		assertEquals(page, service.findAllFreeRoomsPaginated(pageable));
	}

	@Test
	void testFindAllFreeRoomsPaginated() {
		Page<Room> page = mock(Page.class);
		when(roomRepoMock.findAllByRoomStatusId(1, PageRequest.of(1, 5, Sort.by("roomType.type")))).thenReturn(page);
		assertEquals(page, service.findAllFreeRoomsPaginated(1, 5));
	}

	@Test
	void testDeleteRoomById() {
		service.deleteRoomById(1L);
		verify(roomRepoMock).deleteById(anyLong());
		;
	}

	@Test
	void testChangeRoomStatus() {
		service.changeRoomStatus(1L, 2);
		verify(roomRepoMock).changeRoomStatus(anyLong(), anyInt());
	}

}
