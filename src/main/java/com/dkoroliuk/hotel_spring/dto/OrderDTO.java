package com.dkoroliuk.hotel_spring.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.dkoroliuk.hotel_spring.entity.OrderStatus;
import com.dkoroliuk.hotel_spring.entity.Room;
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
public class OrderDTO {
    private long id;
    private LocalDateTime orderDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkInDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOutDate;
    private LocalDate oldCheckOutDate;
    private int totalCost;
    private OrderStatus orderStatus;
    private int oldOrderStatusId;
    private User user;
    private Room room;

}
