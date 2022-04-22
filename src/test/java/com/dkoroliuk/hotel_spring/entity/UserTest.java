package com.dkoroliuk.hotel_spring.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UserTest {
	 @Test
	    void testConstructorAndGetters() throws Exception {
	        UserRole ur = new UserRole(3,"user");
	        User user = new User(100L, "name", "surname", "login", "password", "email", ur, true);
	        assertEquals(100L, user.getId());
	        assertEquals("name", user.getName());
	        assertEquals("surname", user.getSurname());
	        assertEquals("login", user.getLogin());
	        assertEquals("password", user.getPassword());
	        assertEquals("email", user.getEmail());
	        assertEquals(3, user.getUserRole().getId());
	        assertEquals("user", user.getUserRole().getRole());
	        assertTrue(user.isEnable());
	    }

	    @Test
	    void equalsHashcodeVerify() {
	    	UserRole ur = new UserRole(3,"user");
	        User user1 = new User(100L, "name", "surname", "login", "password", "email", ur, true);
	        User user2 = new User(100L, "name", "surname", "login", "password", "email", ur, true);

	        assertEquals(user1, user2);
	        assertEquals(user1.hashCode(), user2.hashCode());
	    }
}
