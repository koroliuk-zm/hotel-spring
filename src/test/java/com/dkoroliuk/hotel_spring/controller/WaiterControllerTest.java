package com.dkoroliuk.hotel_spring.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
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

import com.dkoroliuk.hotel_spring.dto.UserDTO;
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
import com.dkoroliuk.hotel_spring.util.Path;
import com.dkoroliuk.hotel_spring.validators.OrderDTOValidator;

@WithUserDetails(value = "waiter")
@SpringBootTest
@AutoConfigureMockMvc
class WaiterControllerTest {
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@MockBean
	UserService userService;
	@MockBean
	OrderService orderService;
	@MockBean
	RoomService roomService;
	@MockBean
	RequestService requestService;
	@MockBean
	OrderDTOValidator orderDTOValidator;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void waiterPageShouldReturnWaiterPage() throws Exception {
		Room room = new Room();
		room.setRoomType(new RoomType(1, "standart"));
		room.setRoomStatus(new RoomStatus(1, "free"));
		List<Room> roomList = Collections.singletonList(room);
		Page<Room> rooms = new PageImpl<>(roomList);
		Request request = new Request();
		User user = new User();
		request.setUser(user);
		request.setRoomType(new RoomType(1, "standart"));
		List<Request> requestList = Collections.singletonList(request);
		Page<Request> requests = new PageImpl<>(requestList);
		when(userService.findUserByLogin(anyString())).thenReturn(new UserDTO());
		when(requestService.findAllRequestsPageable(anyInt(), anyInt())).thenReturn(requests);
		when(roomService.findAllFreeRoomsPaginated(anyInt(), anyInt())).thenReturn(rooms);
		this.mockMvc.perform(get("/waiter")).andExpect(status().isOk()).andExpect(view().name(Path.WAITER_PAGE))
				.andExpect(model().attributeExists("userDTO", "currentPage", "pageNumbers", "roomsCurrentPage",
						"roomsPageNumbers"));
	}

	@Test
	void editBookOrderPageShouldReturnOrderEditPage() throws Exception {
		Request request = new Request();
		User user = new User();
		request.setUser(user);
		when(requestService.findRequestById(anyLong())).thenReturn(request);
		when(userService.findUserById(anyLong())).thenReturn(user);
		this.mockMvc.perform(get("/waiter/orders/edit/{id}", 1L)).andExpect(status().isOk())
				.andExpect(view().name(Path.WAITER_ORDER_EDIT_PAGE))
				.andExpect(model().attributeExists("orderDTO", "requestDTO"));

	}

	@Test
	void updateBookOrderWhenOrderValidShouldRedirectToWaiterPage() throws Exception {
		Order order = new Order();
		BindingResult errors = mock(BindingResult.class);
		doNothing().when(orderDTOValidator).validate(any(), any());
		when(orderService.updateOrder(anyLong(), any())).thenReturn(order);
		when(errors.hasErrors()).thenReturn(false);
		this.mockMvc.perform(post("/waiter/orders/{id}", 1L, order)).andExpect(status().is3xxRedirection())
				.andExpect(view().name(Path.WAITER_PAGE_REDIRECT));
	}

}
