package com.dkoroliuk.hotel_spring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dkoroliuk.hotel_spring.dto.UserDTO;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.repository.UserRepo;
import com.dkoroliuk.hotel_spring.service.UserService;
import com.dkoroliuk.hotel_spring.util.DTOHelper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = { @Autowired })
@Service
public class UserServiceImpl implements UserService {
	private UserRepo userRepo;
	private PasswordEncoder passwordEncoder;

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public User saveUser(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setSurname(userDTO.getSurname());
		user.setLogin(userDTO.getLogin());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setEmail(userDTO.getEmail());
		user.setUserRole(userDTO.getUserRole());
		user.setEnable(userDTO.isEnable());
		return userRepo.save(user);
	}

	public User findUserById(Long id) {
		return userRepo.getById(id);
	}

	public User updateUser(Long id, UserDTO userDTO) {
		User user = userRepo.getById(id);
		user.setName(userDTO.getName());
		user.setSurname(userDTO.getSurname());
		user.setLogin(userDTO.getLogin());
		if (!userDTO.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		}
		user.setEmail(userDTO.getEmail());
		user.setUserRole(userDTO.getUserRole());
		user.setEnable(userDTO.isEnable());
		return userRepo.save(user);
	}

	public void deleteUserById(Long id) {
		userRepo.deleteById(id);

	}

	public UserDTO findUserByLogin(String login) {
		return userRepo.findUserByLogin(login).map(DTOHelper::toDTO).orElse(null);
	}

	@Override
	public Page<User> getAllUsersPageable(int page, Integer size) {
		return userRepo.findAll(PageRequest.of(page, size, Sort.by("userRole.role")));
	}

}
