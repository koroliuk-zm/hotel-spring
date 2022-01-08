package com.dkoroliuk.hotel_spring.dto;

import com.dkoroliuk.hotel_spring.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String passwordConfirm;
    private String email;
    private UserRole userRole;
    private boolean isEnable;
}
