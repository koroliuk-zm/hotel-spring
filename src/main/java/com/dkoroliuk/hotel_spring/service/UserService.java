package com.dkoroliuk.hotel_spring.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dkoroliuk.hotel_spring.dto.UserDTO;
import com.dkoroliuk.hotel_spring.entity.User;

public interface UserService {
    List<User> getAllUsers();

    User saveUser(UserDTO userDTO);

    User findUserById(Long id);

    User updateUser(Long id, UserDTO userDTO);

    void deleteUserById(Long id);

    UserDTO findUserByLogin(String login);

	Page<User> getAllUsersPageable(int page, Integer size);

}
