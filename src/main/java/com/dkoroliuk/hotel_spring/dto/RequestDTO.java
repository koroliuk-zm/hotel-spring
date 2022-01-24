package com.dkoroliuk.hotel_spring.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.dkoroliuk.hotel_spring.entity.RoomType;
import com.dkoroliuk.hotel_spring.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
	  private long id;
	    private LocalDateTime requestDate;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private LocalDate checkInDate;
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private LocalDate checkOutDate;
	    private int seatsNumber;
	    private User user;
	    private RoomType roomType;
	    private boolean isProceed;
}
