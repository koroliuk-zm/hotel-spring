package com.dkoroliuk.hotel_spring.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.entity.UserRole;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepoTest {
    
	@Autowired
	UserRepo userRepo;
	@Test
	void testFindUserByLoginReturnUserDetails(){
		User savedUser = userRepo.save(new User(100L, "Ivan", "Petrov", "ipetrov", "123456", "ipetrov@gmail.com", new UserRole(3, "user"), true));
		Optional<User> user = userRepo.findUserByLogin("ipetrov");
		assertEquals(true, user.get().getName().equals(savedUser.getName()));
	}
	

}
