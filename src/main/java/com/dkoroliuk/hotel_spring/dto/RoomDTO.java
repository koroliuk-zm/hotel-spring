package com.dkoroliuk.hotel_spring.dto;

import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.entity.RoomType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
	private long id;
	private int number;
	private int seatsAmount;
	private int perdayCost;
	private RoomStatus roomStatus;
	private RoomType roomType;
	private String description;
}
