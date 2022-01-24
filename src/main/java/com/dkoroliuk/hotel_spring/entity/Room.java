package com.dkoroliuk.hotel_spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "rooms")
public class Room {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@Column(name = "number", nullable = false)
	private int number;
	@Column(name = "seats_amount", nullable = false)
    private int seatsAmount;
	@Column(name = "perday_cost", nullable = false)
    private int perdayCost;
	@ManyToOne
    @JoinColumn(name = "room_statuses_id", referencedColumnName = "id", nullable = false)
    private RoomStatus roomStatus;
	@ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name = "room_types_id", referencedColumnName = "id", nullable = false)
    private RoomType roomType;
	@Column(name = "description", nullable = false, length = 1000)
    private String description;
}
