package com.dkoroliuk.hotel_spring.util;

import java.util.List;
import java.util.stream.Collectors;

import com.dkoroliuk.hotel_spring.dto.OrderDTO;
import com.dkoroliuk.hotel_spring.dto.RequestDTO;
import com.dkoroliuk.hotel_spring.dto.RoomDTO;
import com.dkoroliuk.hotel_spring.dto.UserDTO;
import com.dkoroliuk.hotel_spring.entity.Order;
import com.dkoroliuk.hotel_spring.entity.Request;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.User;

/**
 * Utility class that helps with mapping entities to its DTOs
 */
public class DTOHelper {

    /**
     * Secure constructor, to prevent instantiating this class
     */
    private DTOHelper() {
    }

    /**
     * Maps {@link User} to {@link UserDTO}
     * @param user {@link User} to be mapped
     * @return {@link UserDTO}
     */
    public static UserDTO toDTO(User user) {
        return UserDTO
                .builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .login(user.getLogin())
                .password(user.getPassword())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .isEnable(user.isEnable())
                .build();
    }

    /**
     * Maps list of {@link User}s to list of {@link UserDTO}s
     * @param users list of {@link User}s to be mapped
     * @return list of {@link UserDTO}s
     */
    public static List<UserDTO> userListToDTO(List<User> users) {
        return users.stream().map(DTOHelper::toDTO).collect(Collectors.toList());
    }
    
    /**
     * Maps {@link Room} to {@link RoomDTO}
     * @param room {@link Room} to be mapped
     * @return {@link RoomDTO}
     */
    public static RoomDTO toDTO(Room room) {
		return RoomDTO
				.builder()
				.id(room.getId())
				.number(room.getNumber())
				.seatsAmount(room.getSeatsAmount())
				.perdayCost(room.getPerdayCost())
				.roomStatus(room.getRoomStatus())
				.roomType(room.getRoomType())
				.description(room.getDescription())
				.build();    	
    }
    
    /**
     * Maps list of {@link Room}s to list of {@link RoomDTO}s
     * @param rooms list of {@link Room}s to be mapped
     * @return list of {@link RoomDTO}s
     */
    public static List<RoomDTO> roomListToDTO(List<Room> rooms) {
        return rooms.stream().map(DTOHelper::toDTO).collect(Collectors.toList());
    }
    
    /**
     * Maps {@link Order} to {@link OrderDTO}
     * @param room {@link Order} to be mapped
     * @return {@link OrderDTO}
     */
    public static OrderDTO toDTO(Order order) {
		return OrderDTO
				.builder()
				.id(order.getId())
				.orderDate(order.getOrderDate())
				.checkInDate(order.getCheckInDate())
				.checkOutDate(order.getCheckOutDate())
				.totalCost(order.getTotalCost())
				.orderStatus(order.getOrderStatus())
				.user(order.getUser())
				.room(order.getRoom())
				.build();    	
    }
    
    /**
     * Maps list of {@link Order}s to list of {@link RoomDTO}s
     * @param orders list of {@link Order}s to be mapped
     * @return list of {@link OrderDTO}s
     */
    public static List<OrderDTO> orderListToDTO(List<Order> orders) {
        return orders.stream().map(DTOHelper::toDTO).collect(Collectors.toList());
    }
    
    /**
     * Maps {@link Request} to {@link RequestDTO}
     * @param room {@link Request} to be mapped
     * @return {@link RequestDTO}
     */
    public static RequestDTO toDTO(Request requsest) {
    	return RequestDTO
    			.builder()
    			.id(requsest.getId())
    			.requestDate(requsest.getRequestDate())
    			.checkInDate(requsest.getCheckInDate())
    			.checkOutDate(requsest.getCheckOutDate())
    			.seatsNumber(requsest.getSeatsNumber())
    			.user(requsest.getUser())
    			.roomType(requsest.getRoomType())
    			.build();
    }
    
    /**
     * Maps list of {@link Request}s to list of {@link RequestDTO}s
     * @param request list of {@link Request}s to be mapped
     * @return list of {@link RequestDTO}s
     */
    public static List<RequestDTO> requestListToDTO(List<Request> requests) {
        return requests.stream().map(DTOHelper::toDTO).collect(Collectors.toList());
    }
}
