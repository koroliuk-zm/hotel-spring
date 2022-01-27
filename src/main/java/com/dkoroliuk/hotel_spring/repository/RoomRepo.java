package com.dkoroliuk.hotel_spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dkoroliuk.hotel_spring.entity.Room;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {

	Page<Room> findAllByRoomStatusId(int id, Pageable pageable);

	@Transactional
	@Modifying
	@Query("UPDATE Room r SET r.roomStatus.id=?2 WHERE r.id = ?1")
	void changeRoomStatus(long roomId, int newStatusId);

	Room getRoomByNumber(int number);
}
