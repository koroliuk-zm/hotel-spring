package com.dkoroliuk.hotel_spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dkoroliuk.hotel_spring.entity.AppUserDetails;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.repository.UserRepo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
public class AppUserDetailsService implements UserDetailsService {
	@Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findUserByLogin(username);
        return user.map(AppUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("No such user as " + username));
    }
}
