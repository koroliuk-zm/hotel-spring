package com.dkoroliuk.hotel_spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dkoroliuk.hotel_spring.dto.RoomDTO;
import com.dkoroliuk.hotel_spring.entity.Room;

public interface RoomService {
	List<Room> getAllRooms();

    Room saveRoom(RoomDTO roomDTO);

    Page<Room> getAllRoomsPageable(Pageable pageable);

	Room updateRoom(Long id, RoomDTO roomDTO);

	void deleteRoomById(Long id);

	Page<Room> findAllFreeRoomsPaginated(Pageable pageable);

	Room findRoomById(Long id);

	Page<Room> findAllFreeRoomsPaginated(int i, Integer size);

	void changeRoomStatusToFree(long roomId);

	void changeRoomStatusToBusy(long roomId);

}
