package com.dkoroliuk.hotel_spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.dkoroliuk.hotel_spring.dto.UserDTO;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.entity.UserRole;
import com.dkoroliuk.hotel_spring.repository.UserRepo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class UserServiceTest {
@Autowired
UserService userService;
@Autowired
private UserRepo userRepo;

@DisplayName("Returning saved user from service layer")	
@Test
void testFindUserByIdForSavedUser() {
	User savedUser = userRepo.save(new User(100L, "Ivan", "Petrov", "ipetrov", "123456", "ipetrov@gmail.com", new UserRole(3, "user"), true));
	User user = userService.findUserById(savedUser.getId());
	assertEquals(savedUser.getLogin(), user.getLogin());
	assertEquals(savedUser.getSurname(), user.getSurname());
}

@Test
void testSaveUser() {
	User user = userService.saveUser(new UserDTO(100L, "Ivan", "Petrov", "ipetrov", "123456", "123456", "ipetrov@gmail.com", new UserRole(3, "user"), true));
	assertEquals("ipetrov", user.getLogin());
	assertEquals("Petrov", user.getSurname());
}



@Test
void testGetAllUsers() {
	List<User> allUsersPrevious = userService.getAllUsers();
	userRepo.save(new User(100L, "Ivan", "Petrov", "ipetrov", "123456", "ipetrov@gmail.com", new UserRole(3, "user"), true));
	userRepo.save(new User(101L, "Petro", "Demidiv", "pdemidiv", "123456", "pdemidiv@gmail.com", new UserRole(3, "user"), true));
	userRepo.save(new User(102L, "Mikhail", "Svets", "msvets", "123456", "msvets@gmail.com", new UserRole(3, "user"), true));
	List<User> allUsers = userService.getAllUsers();
	assertEquals(true, allUsers.size()>0);
	assertEquals(allUsersPrevious.size()+3, allUsers.size());
}

@Test
void testUpdateUser() {
	User savedUser = userRepo.save(new User(100L, "Ivan", "Petrov", "ipetrov", "123456", "ipetrov@gmail.com", new UserRole(3, "user"), true));
	User updatedUser = userService.updateUser(savedUser.getId(), new UserDTO(100L, "Ivan", "Petrov", "ipetriv", "123456", "123456", "ipetrov@gmail.com", new UserRole(3, "user"), true));
	assertEquals("ipetriv", updatedUser.getLogin());
}

@Test
void testFindUserByLogin() {
	User savedUser = userRepo.save(new User(100L, "Ivan", "Petrov", "ipetrov", "123456", "ipetrov@gmail.com", new UserRole(3, "user"), true));
	UserDTO foundedUser = userService.findUserByLogin("ipetrov");
	assertEquals(foundedUser.getEmail(), savedUser.getEmail());
}
}
