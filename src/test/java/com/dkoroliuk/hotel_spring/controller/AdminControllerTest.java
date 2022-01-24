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
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.entity.RoomType;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.entity.UserRole;
import com.dkoroliuk.hotel_spring.service.RoomService;
import com.dkoroliuk.hotel_spring.service.UserService;
import com.dkoroliuk.hotel_spring.util.Path;
import com.dkoroliuk.hotel_spring.validators.RoomDTOValidator;
import com.dkoroliuk.hotel_spring.validators.UserDTOValidator;

@WithUserDetails(value = "dkoroliuk")
@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc
class AdminControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
    private WebApplicationContext webApplicationContext;
	@MockBean
	private UserService userService;
	@MockBean
	private UserDTOValidator userDTOValidator;
	@MockBean
	private RoomDTOValidator roomDTOValidator;
	@MockBean
	private RoomService roomService;
	
	@BeforeEach
    void setUp() {
    	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
	@Test
    void adminPageShouldReturnAdminPage() throws Exception {
		UserDTO user = new UserDTO();
        when(userService.findUserByLogin(anyString())).thenReturn(user);
        this.mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name(Path.ADMIN_PAGE))
                .andExpect(model().attribute("userDTO", user));
    }
	
	
	@Test
    void getAllUsersPageShouldReturnAllUsersPage() throws Exception {
	      User user = new User();
	        user.setId(1L);
	        user.setName("name");
	        user.setSurname("surname");
	        user.setLogin("login");
	        user.setPassword("password");
	        user.setUserRole(new UserRole(3, "user"));
	        user.setEmail("email@mail.com");
	        user.setEnable(true);
	        List<User> userList = new ArrayList<>();
	        userList.add(user);
	        Page<User> users = new PageImpl<>(userList);
	        when(userService.getAllUsersPageable(anyInt(), anyInt())).thenReturn(users);
	        this.mockMvc.perform(get("/admin/users"))
	                .andExpect(status().isOk())
	                .andExpect(view().name(Path.ADMIN_ALL_USERS_PAGE))
	                .andExpect(model().attributeExists("currentPage", "pageNumbers"));
    }
	
	@Test
	void getUserFormForAdminShouldReturnAdminUserForm() throws Exception {
        this.mockMvc.perform(get("/admin/users/new_user", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name(Path.ADMIN_USER_FORM))
                .andExpect(model().attributeExists("userDTO"));
	}
	
    @Test
    void addNewUserShouldRedirectToAdminAllUsersPage() throws Exception {
        when(userService.saveUser(any())).thenReturn(new User());
        this.mockMvc.perform(post("/admin/users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(Path.REDIRECT_ADMIN_ALL_USERS_PAGE));
    }
    
    @Test
    void editUserFormShouldReturnUserEditPage() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(new User());
        this.mockMvc.perform(get("/admin/users/edit/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name(Path.EDIT_USER_PAGE))
                .andExpect(model().attributeExists("userDTO"));
    }
    
    @Test
    void updateUserWhenUserValidShouldRedirectToAdminAllUsersPage() throws Exception {
    	User user = new User();
        BindingResult errors = mock(BindingResult.class);
        doNothing().when(userDTOValidator).validate(any(), any());
        when(userService.updateUser(anyLong(),any())).thenReturn(user);
        when(errors.hasErrors()).thenReturn(false);
        this.mockMvc.perform(post("/admin/users/{id}", 1L, user))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(Path.REDIRECT_ADMIN_ALL_USERS_PAGE));
    }
    
	@Test
    void getAllRoomsPageShouldReturnAllRoomsPage() throws Exception {
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
	        Page<Room> rooms = new PageImpl<>(roomList);
		 when(roomService.getAllRoomsPageable(any())).thenReturn(rooms);
	        this.mockMvc.perform(get("/admin/rooms"))
	                .andExpect(status().isOk())
	                .andExpect(view().name(Path.ADMIN_ALL_ROOMS_PAGE))
	                .andExpect(model().attributeExists("pagingParam", "roomPage", "pageNumbers"));
    }
	
	@Test
	void getRoomFormForAdminShouldReturnAdminRoomForm() throws Exception {
        this.mockMvc.perform(get("/admin/rooms/new_room", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name(Path.ADMIN_ROOM_FORM))
                .andExpect(model().attributeExists("roomDTO"));
	}

    @Test
    void addNewRoomShouldRedirectToAdminAllRoomsPage() throws Exception {
        when(roomService.saveRoom(any())).thenReturn(new Room());
        this.mockMvc.perform(post("/admin/rooms"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(Path.REDIRECT_ADMIN_ALL_ROOMS_PAGE));
    }
    
    @Test
    void editRoomFormShouldReturnRoomEditPage() throws Exception {
        when(roomService.findRoomById(anyLong())).thenReturn(new Room());
        this.mockMvc.perform(get("/admin/rooms/edit/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name(Path.EDIT_ROOM_PAGE))
                .andExpect(model().attributeExists("roomDTO"));
    }
    
    @Test
    void updateRoomWhenRoomValidShouldRedirectToAdminAllRoomsPage() throws Exception {
    	Room room = new Room();
        BindingResult errors = mock(BindingResult.class);
        doNothing().when(roomDTOValidator).validate(any(), any());
        when(roomService.updateRoom(anyLong(),any())).thenReturn(room);
        when(errors.hasErrors()).thenReturn(false);
        this.mockMvc.perform(post("/admin/rooms/{id}", 1L, room))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(Path.REDIRECT_ADMIN_ALL_ROOMS_PAGE));
    }
}
