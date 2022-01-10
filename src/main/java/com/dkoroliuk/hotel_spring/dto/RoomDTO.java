package com.dkoroliuk.hotel_spring.dto;

import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.entity.RoomType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
	private long id;
	private int number;
	private int seatsAmount;
	private int perdayCost;
	private int perdayCostOld;
	private RoomStatus roomStatus;
	private RoomStatus roomStatusOld;
	private RoomType roomType;
	private String description;
}
