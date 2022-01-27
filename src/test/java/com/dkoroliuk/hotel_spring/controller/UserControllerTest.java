package com.dkoroliuk.hotel_spring.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import com.dkoroliuk.hotel_spring.dto.OrderDTO;
import com.dkoroliuk.hotel_spring.dto.RequestDTO;
import com.dkoroliuk.hotel_spring.entity.Order;
import com.dkoroliuk.hotel_spring.entity.Request;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.entity.RoomType;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.service.OrderService;
import com.dkoroliuk.hotel_spring.service.RequestService;
import com.dkoroliuk.hotel_spring.service.RoomService;
import com.dkoroliuk.hotel_spring.service.UserService;
import com.dkoroliuk.hotel_spring.util.DTOHelper;
import com.dkoroliuk.hotel_spring.util.Path;
import com.dkoroliuk.hotel_spring.validators.OrderDTOValidator;
import com.dkoroliuk.hotel_spring.validators.RequestDTOValidator;

@WithUserDetails(value = "newuser")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@MockBean
	private UserService userService;
	@MockBean
	private RoomService roomService;
	@MockBean
	private OrderService roomOrderService;
	@MockBean
	private RequestService requestService;
	@MockBean

	OrderDTOValidator orderDTOValidator;
	@MockBean
	RequestDTOValidator requestDTOValidator;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void userPageShouldReturnUserPage() throws Exception {
		User user = new User();
		List<OrderDTO> roomOrders = new ArrayList<>();
		List<OrderDTO> ordersHistory = new ArrayList<>();
		List<RequestDTO> requests = new ArrayList<>();
		when(userService.findUserById(anyLong())).thenReturn(user);
		when(roomOrderService.findUsersOngoingOrders(anyLong())).thenReturn(roomOrders);
		when(roomOrderService.findUsersProcessedOrders(anyLong())).thenReturn(ordersHistory);
		when(requestService.findUsersRequests(anyLong())).thenReturn(requests);
		this.mockMvc.perform(get("/user")).andExpect(status().isOk()).andExpect(view().name(Path.USER_PAGE))
				.andExpect(model().attribute("roomOrders", roomOrders))
				.andExpect(model().attribute("ordersHistory", ordersHistory))
				.andExpect(model().attribute("newOrders", requests)).andExpect(model().attribute("userDTO", user));
	}

	@Test
	void freeRoomsPageShouldReturnRoomsPage() throws Exception {
		Room room = new Room();
		room.setId(1L);
		room.setNumber(1);
		room.setSeatsAmount(1);
		room.setPerdayCost(1);
		room.setRoomStatus(new RoomStatus(1, "free"));
		room.setRoomType(new RoomType(1, "standart"));
		room.setDescription("description");
		List<Room> roomList = new ArrayList<>();
		roomList.add(room);
		Page<Room> rooms = new PageImpl<>(roomList);
		when(roomService.findAllFreeRoomsPaginated(any())).thenReturn(rooms);
		this.mockMvc.perform(get("/user/rooms")).andExpect(status().isOk()).andExpect(view().name(Path.USER_ROOMS_PAGE))
				.andExpect(model().attributeExists("roomPage", "pagingParam", "pageNumbers"));
	}

	@Test
	void roomOrderPageShouldReturnRoomOrderPage() throws Exception {
		Room room = new Room();
		when(roomService.findRoomById(anyLong())).thenReturn(room);
		this.mockMvc.perform(get("/user/rooms/order/{id}", 1L)).andExpect(status().isOk())
				.andExpect(view().name(Path.USER_ROOM_ORDER_PAGE)).andExpect(model().attributeExists("orderDTO"));
	}
	
	@Test
	void roomNewOrderPageShouldReturnRoomNewOrderPage() throws Exception {
		this.mockMvc.perform(get("/user/rooms/order/create_new_order/", 1L)).andExpect(status().isOk())
				.andExpect(view().name(Path.USER_CREATE_ORDER_PAGE)).andExpect(model().attributeExists("requestDTO"));
	}

	@Test
	void saveNewRequestShouldRedirectToRoomsPage() throws Exception {
		BindingResult errors = mock(BindingResult.class);
		doNothing().when(requestDTOValidator).validate(any(), any());
		when(requestService.saveNewRequest(any())).thenReturn(new RequestDTO());
		when(errors.hasErrors()).thenReturn(false);
		this.mockMvc.perform(post("/user/rooms/order/create_new_order/")).andExpect(status().is3xxRedirection())
				.andExpect(view().name(Path.USER_ROOMS_REDIRECT));
	}

}
