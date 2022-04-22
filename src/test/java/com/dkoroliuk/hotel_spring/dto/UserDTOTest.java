package com.dkoroliuk.hotel_spring.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dkoroliuk.hotel_spring.entity.UserRole;

public class UserDTOTest {
	 @Test
	    void testConstructorAndGetters() throws Exception {
	        UserRole ur = new UserRole(3,"user");
	        UserDTO user = new UserDTO(1L, "name", "surname", "login", "password", "password", "email", ur, true);
	        assertEquals(1L, user.getId());
	        assertEquals("name", user.getName());
	        assertEquals("surname", user.getSurname());
	        assertEquals("login", user.getLogin());
	        assertEquals("password", user.getPassword());
	        assertEquals("password", user.getPasswordConfirm());
	        assertEquals("email", user.getEmail());
	        assertEquals(3, user.getUserRole().getId());
	        assertEquals("user", user.getUserRole().getRole());
	        assertTrue(user.isEnable());
	    }

	    @Test
	    void equalsHashcodeVerify() {
	    	UserRole ur = new UserRole(3,"user");
	    	UserDTO user1 = new UserDTO(1L, "name", "surname", "login", "password", "password", "email", ur, true);
	    	UserDTO user2 = new UserDTO(1L, "name", "surname", "login", "password", "password", "email", ur, true);

	        assertEquals(user1, user2);
	        assertEquals(user1.hashCode(), user2.hashCode());
	    }
}
