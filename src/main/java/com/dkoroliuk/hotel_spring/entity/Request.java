package com.dkoroliuk.hotel_spring.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "request")
public class Request {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;
	@Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;
	@Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;
	@Column(name = "seats_number", nullable = false)
    private int seatsNumber;
	@ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = false)
	private User user;
	@ManyToOne
    @JoinColumn(name = "room_types_id", referencedColumnName = "id", nullable = false)
	private RoomType roomType;
	 @Column(name = "is_proceed", nullable = false)
	  private boolean isProceed;
}
