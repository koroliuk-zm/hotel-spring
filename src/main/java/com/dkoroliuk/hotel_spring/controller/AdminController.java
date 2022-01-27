package com.dkoroliuk.hotel_spring.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dkoroliuk.hotel_spring.dto.RoomDTO;
import com.dkoroliuk.hotel_spring.dto.UserDTO;
import com.dkoroliuk.hotel_spring.entity.AppUserDetails;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.service.RoomService;
import com.dkoroliuk.hotel_spring.service.UserService;
import com.dkoroliuk.hotel_spring.util.DTOHelper;
import com.dkoroliuk.hotel_spring.util.Pagination;
import com.dkoroliuk.hotel_spring.util.Pagination.RoomPaginationParam;
import com.dkoroliuk.hotel_spring.util.Path;
import com.dkoroliuk.hotel_spring.validators.RoomDTOValidator;
import com.dkoroliuk.hotel_spring.validators.UserDTOValidator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Administrator related controller
 */

@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = { @Autowired })
@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	public static final String CURRENT_PAGE = "currentPage";
	public static final String PAGE_NUMBERS = "pageNumbers";
	private UserService userService;
	private UserDTOValidator userDTOValidator;
	private RoomDTOValidator roomDTOValidator;
	private RoomService roomService;

	@GetMapping
	public String adminPage(Model model) {
		model.addAttribute("userDTO", userService.findUserByLogin(getAdministratorLogin()));
		return Path.ADMIN_PAGE;
	}

	@GetMapping("/users")
	public String getAllUsers(Model model, @RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "5") Integer size) {
		Page<User> currentPage = userService.getAllUsersPageable(page - 1, size);
		model.addAttribute(CURRENT_PAGE, currentPage);
		model.addAttribute(PAGE_NUMBERS, Pagination.buildPageNumbers(currentPage.getTotalPages()));
		return Path.ADMIN_ALL_USERS_PAGE;
	}

	@GetMapping("/users/new_user")
	public String getUserFormForAdmin(Model model) {
		model.addAttribute(new UserDTO());
		String message = String.format("Administrator login %s is going to add new user", getAdministratorLogin());
		logger.info(message);
		return Path.ADMIN_USER_FORM;
	}

	@PostMapping("/users")
	public String addNewUser(@ModelAttribute UserDTO userDTO, BindingResult errors, Model model) {
		userDTOValidator.validate(userDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute(userDTO);
			String message = String.format("Administrator login %s failed to add new user", getAdministratorLogin());
			logger.info(message);
			return Path.ADMIN_USER_FORM;
		}
		User user = userService.saveUser(userDTO);
		String message = String.format("Administrator login %s added new user login %s", getAdministratorLogin(),
				user.getLogin());
		logger.info(message);
		return Path.REDIRECT_ADMIN_ALL_USERS_PAGE;
	}

	/**
	 * Method to access {@link User} edit page. Handles GET request for URL
	 * "/administrator/users/edit/{id}".
	 * 
	 * @param id    {@link User} id
	 * @param model instance of {@link Model}
	 * @return view "/administrator/edit_user"
	 */
	@GetMapping("/users/edit/{id}")
	public String editUserForm(@PathVariable Long id, Model model) {
		model.addAttribute(DTOHelper.toDTO(userService.findUserById(id)));
		String message = String.format("Administrator login %s is going to update user id %s", getAdministratorLogin(),
				id);
		logger.info(message);
		return Path.EDIT_USER_PAGE;
	}

	@PostMapping("/users/{id}")
	public String updateUser(@PathVariable Long id, @ModelAttribute UserDTO userDTO, BindingResult errors,
			Model model) {
		userDTOValidator.validate(userDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute(userDTO);
			String message = String.format("Administrator login %s failed to update user id %s",
					getAdministratorLogin(), id);
			logger.info(message);

			return Path.EDIT_USER_PAGE;
		}
		userService.updateUser(id, userDTO);
		String message = String.format("Administrator login %s updated user id %s", getAdministratorLogin(), id);
		logger.info(message);
		return Path.REDIRECT_ADMIN_ALL_USERS_PAGE;
	}

	@PostMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
		String message = String.format("Administrator login %s deleted user id %s", getAdministratorLogin(), id);
		logger.info(message);

		return Path.REDIRECT_ADMIN_ALL_USERS_PAGE;
	}

	@GetMapping("/rooms")
	public String getAllRooms(Model model, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size,
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
		Page<Room> roomPage = roomService.getAllRoomsPageable(pageable);
		model.addAttribute("roomPage", roomPage);
		model.addAttribute(PAGE_NUMBERS, Pagination.buildPageNumbers(roomPage.getTotalPages()));
		return Path.ADMIN_ALL_ROOMS_PAGE;
	}

	@GetMapping("/rooms/new_room")
	public String getRoomFormforAdmin(Model model) {
		model.addAttribute(new RoomDTO());
		String message = String.format("Administrator login %s going to add new room", getAdministratorLogin());
		logger.info(message);
		return Path.ADMIN_ROOM_FORM;
	}

	@PostMapping("/rooms")
	public String addNewRoom(@ModelAttribute RoomDTO roomDTO, BindingResult errors, Model model) {
		roomDTOValidator.validate(roomDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute(roomDTO);
			String message = String.format("Administrator login %s failed to add new room", getAdministratorLogin());
			logger.info(message);
			return Path.ADMIN_USER_FORM;
		}
		Room room = roomService.saveRoom(roomDTO);
		String message = String.format("Administrator login %s added new room number %s", getAdministratorLogin(),
				room.getNumber());
		logger.info(message);

		return Path.REDIRECT_ADMIN_ALL_ROOMS_PAGE;
	}

	/**
	 * Method to access {@link Room} edit page. Handles GET request for URL
	 * "/admin/rooms/edit/{id}".
	 * 
	 * @param id    {@link Room} id
	 * @param model instance of {@link Model}
	 * @return view "/admin/edit_room"
	 */
	@GetMapping("/rooms/edit/{id}")
	public String roomEditForm(@PathVariable Long id, Model model) {
		model.addAttribute(DTOHelper.toDTO(roomService.findRoomById(id)));
		String message = String.format("Administrator login %s going to edit room id %s", getAdministratorLogin(), id);
		logger.info(message);

		return Path.EDIT_ROOM_PAGE;
	}

	@PostMapping("/rooms/{id}")
	public String updateRoom(@PathVariable Long id, @ModelAttribute RoomDTO roomDTO, BindingResult errors,
			Model model) {
		roomDTOValidator.validate(roomDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute(roomDTO);
			String message = String.format("Administrator id %s failed to update book id %s", getAdministratorLogin(),
					id);
			logger.info(message);
			return Path.EDIT_ROOM_PAGE;
		}
		roomService.updateRoom(id, roomDTO);

		String message = String.format("Administrator login %s updated room id %s", getAdministratorLogin(), id);
		logger.info(message);

		return Path.REDIRECT_ADMIN_ALL_ROOMS_PAGE;
	}

	@PostMapping("/rooms/delete/{id}")
	public String deleteRoomById(@PathVariable Long id) {
		roomService.deleteRoomById(id);
		String message = String.format("Administrator id %s deleted book id %s", getAdministratorLogin(), id);
		logger.info(message);

		return Path.REDIRECT_ADMIN_ALL_ROOMS_PAGE;
	}

	private String getAdministratorLogin() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((AppUserDetails) principal).getUsername();
	}

}
