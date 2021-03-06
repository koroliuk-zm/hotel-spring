package com.dkoroliuk.hotel_spring.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dkoroliuk.hotel_spring.dto.UserDTO;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.entity.UserRole;
import com.dkoroliuk.hotel_spring.service.RoomService;
import com.dkoroliuk.hotel_spring.service.UserService;
import com.dkoroliuk.hotel_spring.util.Pagination;
import com.dkoroliuk.hotel_spring.util.Pagination.RoomPaginationParam;
import com.dkoroliuk.hotel_spring.util.Path;
import com.dkoroliuk.hotel_spring.validators.UserDTOValidator;

import lombok.AllArgsConstructor;

/**
 * Controller to process requests to index, login, registration pages
 */

@AllArgsConstructor(onConstructor_ = { @Autowired })
@Controller
public class MainController {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private UserService userService;
	private UserDTOValidator userDTOValidator;
	private RoomService roomService;

	@GetMapping("/login")
	public String loginPage() {
		return Path.LOGIN_PAGE;
	}

	@GetMapping("/registration")
	public String registrationPage(Model model) {
		model.addAttribute(new UserDTO());
		return Path.REGISTRATION_PAGE;
	}

	@PostMapping("/registration")
	public String registerNewUser(@ModelAttribute UserDTO userDTO, BindingResult errors, Model model) {
		userDTOValidator.validate(userDTO, errors);
		if (userService.findUserByLogin(userDTO.getLogin()) != null) {
			model.addAttribute("userExist", true);
			model.addAttribute(userDTO);
			String message = String.format("Registration failed for user with login \"%s\", this login already in use!",
					userDTO.getLogin());
			logger.info(message);
			return Path.REGISTRATION_PAGE;
		}
		if (errors.hasErrors()) {
			model.addAttribute(userDTO);
			return Path.REGISTRATION_PAGE;
		}

		userDTO.setUserRole(new UserRole());
		userDTO.getUserRole().setId(3);
		userDTO.setEnable(true);
		userService.saveUser(userDTO);
		String message = String.format("User with login \"%s\" successfully registered", userDTO.getLogin());
		logger.info(message);
		return Path.LOGIN_PAGE_REDIRECT;
	}

	@GetMapping
	public String indexPage(Model model, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size,
			@ModelAttribute Optional<RoomPaginationParam> pagingParam) {
		RoomPaginationParam roomPaginationParam = pagingParam.orElse(new RoomPaginationParam());
		model.addAttribute("pagingParam", roomPaginationParam);
		int currentPage = page.orElse(1);
		int currentSize = size.orElse(5);
		Pageable pageable;
		if (roomPaginationParam.getSortOrder().equals("asc")) {
			pageable = PageRequest.of(currentPage - 1, currentSize, Sort.by(roomPaginationParam.getSortBy()));
		} else {
			pageable = PageRequest.of(currentPage - 1, currentSize,
					Sort.by(roomPaginationParam.getSortBy()).descending());
		}
		Page<Room> roomPage = roomService.findAllFreeRoomsPaginated(pageable);
		model.addAttribute("roomPage", roomPage);
		model.addAttribute("pageNumbers", Pagination.buildPageNumbers(roomPage.getTotalPages()));
		return Path.WELCOME_PAGE;
	}
}
