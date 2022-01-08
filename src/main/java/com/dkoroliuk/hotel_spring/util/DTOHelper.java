package com.dkoroliuk.hotel_spring.util;

import java.util.List;
import java.util.stream.Collectors;

import com.dkoroliuk.hotel_spring.dto.UserDTO;
import com.dkoroliuk.hotel_spring.entity.User;

/**
 * Utility class that helps with mapping entities to its DTOs
 */
public class DTOHelper {

    /**
     * Secure constructor, to prevent instantiating this class
     */
    private DTOHelper() {
    }

    /**
     * Maps {@link User} to {@link UserDTO}
     * @param user {@link User} to be mapped
     * @return {@link UserDTO}
     */
    public static UserDTO toDTO(User user) {
        return UserDTO
                .builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .login(user.getLogin())
                .password(user.getPassword())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .isEnable(user.isEnable())
                .build();
    }

    /**
     * Maps list of {@link User}s to list of {@link UserDTO}s
     * @param users list of {@link User}s to be mapped
     * @return list of {@link UserDTO}s
     */
    public static List<UserDTO> userListToDTO(List<User> users) {
        return users.stream().map(DTOHelper::toDTO).collect(Collectors.toList());
    }
}
