package com.dkoroliuk.hotel_spring.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.entity.RoomType;
import com.dkoroliuk.hotel_spring.service.RoomService;
import com.dkoroliuk.hotel_spring.service.UserService;
import com.dkoroliuk.hotel_spring.util.Path;
import com.dkoroliuk.hotel_spring.validators.UserDTOValidator;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	@MockBean
	private UserService userService;
	@MockBean
	RoomService roomService;
	@MockBean
	UserDTOValidator userDTOValidator;
	private static PageImpl<Room> rooms;

	@Test
	void indexPageShouldReturnIndexPage() throws Exception {
		Room room = new Room();
		room.setId(1L);
		room.setNumber(1);
		room.setSeatsAmount(1);
		room.setPerdayCost(100);
		room.setRoomStatus(new RoomStatus(1, "free"));
		room.setRoomType(new RoomType(1, "standart"));
		room.setDescription("room number1");
		List<Room> roomList = new ArrayList<>();
		roomList.add(room);
		room = new Room();
		room.setId(2L);
		room.setNumber(2);
		room.setSeatsAmount(2);
		room.setPerdayCost(150);
		room.setRoomStatus(new RoomStatus(1, "free"));
		room.setRoomType(new RoomType(1, "standart"));
		room.setDescription("room number2");
		roomList.add(room);
		rooms = new PageImpl<>(roomList);
		when(roomService.findAllFreeRoomsPaginated(any())).thenReturn(rooms);
		this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name(Path.WELCOME_PAGE))
				.andExpect(model().attribute("roomPage", rooms));
	}

	@Test
	void loginPageShouldReturnLoginPage() throws Exception {
		this.mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name(Path.LOGIN_PAGE));
	}

	@Test
	void registrationPageShouldReturnRegistrationPage() throws Exception {
		this.mockMvc.perform(get("/registration")).andExpect(status().isOk())
				.andExpect(view().name(Path.REGISTRATION_PAGE));
	}

	@Test
	void registerNewUserWhenUserValidAndNotExistShouldRedirectToLoginPage() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.mockMvc.perform(post("/registration")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}

	@Test
	void testUserLoginWithBadCredentials() throws Exception {
		this.mockMvc.perform(post("/login").param("login", "user")).andExpect(status().is4xxClientError());
	}

}
