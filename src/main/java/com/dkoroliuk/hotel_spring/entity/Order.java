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
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "order_date", nullable = false)
	private LocalDateTime orderDate;
	@Column(name = "check_in_date", nullable = false)
	private LocalDate checkInDate;
	@Column(name = "check_out_date", nullable = false)
	private LocalDate checkOutDate;
	@Column(name = "total_cost", nullable = false)
	private int totalCost;
	@ManyToOne
	@JoinColumn(name = "order_statuses_id", referencedColumnName = "id", nullable = false)
	private OrderStatus orderStatus;
	@ManyToOne
	@JoinColumn(name = "users_id", referencedColumnName = "id", nullable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "rooms_id", referencedColumnName = "id")
	private Room room;

}
