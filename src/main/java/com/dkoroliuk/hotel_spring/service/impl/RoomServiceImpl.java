package com.dkoroliuk.hotel_spring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dkoroliuk.hotel_spring.dto.RoomDTO;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.repository.RoomRepo;
import com.dkoroliuk.hotel_spring.service.RoomService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = { @Autowired })
@Service
public class RoomServiceImpl implements RoomService {
	private RoomRepo roomRepo;

	@Override
	public List<Room> getAllRooms() {
		return roomRepo.findAll();
	}

	@Override
	public Room saveRoom(RoomDTO roomDTO) {
		Room room = new Room();
		room.setNumber(roomDTO.getNumber());
		room.setSeatsAmount(roomDTO.getSeatsAmount());
		room.setPerdayCost(roomDTO.getPerdayCost());
		room.setRoomStatus(roomDTO.getRoomStatus());
		room.setRoomType(roomDTO.getRoomType());
		room.setDescription(roomDTO.getDescription());
		return roomRepo.save(room);
	}

	@Override
	public Room updateRoom(Long id, RoomDTO roomDTO) {
		Room room = roomRepo.getById(id);
		room.setNumber(roomDTO.getNumber());
		room.setPerdayCost(roomDTO.getPerdayCost());
		room.setSeatsAmount(roomDTO.getSeatsAmount());
		room.setRoomStatus(roomDTO.getRoomStatus());
		room.setRoomType(roomDTO.getRoomType());
		room.setDescription(roomDTO.getDescription());
		return roomRepo.save(room);
	}

	@Override
	public void deleteRoomById(Long id) {
		roomRepo.deleteById(id);

	}

	@Override
	public Page<Room> getAllRoomsPageable(Pageable pageable) {
		return roomRepo.findAll(pageable);
	}

	@Override
	public Page<Room> findAllFreeRoomsPaginated(Pageable pageable) {
		return roomRepo.findAllByRoomStatusId(1, pageable);
	}

	@Override
	public Room findRoomById(Long id) {
		return roomRepo.getById(id);
	}

	@Override
	public Page<Room> findAllFreeRoomsPaginated(int page, Integer size) {
		return roomRepo.findAllByRoomStatusId(1, PageRequest.of(page, size, Sort.by("roomType.type")));
	}

	@Override
	public void changeRoomStatusToFree(long roomId) {
		roomRepo.changeRoomStatus(roomId, 1);

	}

	@Override
	public void changeRoomStatusToBusy(long roomId) {
		roomRepo.changeRoomStatus(roomId, 3);

	}

}
