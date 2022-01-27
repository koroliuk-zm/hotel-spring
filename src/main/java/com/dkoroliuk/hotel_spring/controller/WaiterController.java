package com.dkoroliuk.hotel_spring.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dkoroliuk.hotel_spring.dto.OrderDTO;
import com.dkoroliuk.hotel_spring.dto.RequestDTO;
import com.dkoroliuk.hotel_spring.entity.AppUserDetails;
import com.dkoroliuk.hotel_spring.entity.Order;
import com.dkoroliuk.hotel_spring.entity.OrderStatus;
import com.dkoroliuk.hotel_spring.entity.Request;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.service.OrderService;
import com.dkoroliuk.hotel_spring.service.RequestService;
import com.dkoroliuk.hotel_spring.service.RoomService;
import com.dkoroliuk.hotel_spring.service.UserService;
import com.dkoroliuk.hotel_spring.util.DTOHelper;
import com.dkoroliuk.hotel_spring.util.Pagination;
import com.dkoroliuk.hotel_spring.util.Path;
import com.dkoroliuk.hotel_spring.validators.OrderDTOValidator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Controller for "WAITER" role. Handles all request starts with "/waiter"
 */
@AllArgsConstructor(onConstructor_ = { @Autowired })
@Controller
@RequestMapping("/waiter")
public class WaiterController {
	private static final Logger logger = LoggerFactory.getLogger(WaiterController.class);
	UserService userService;
	RoomService roomService;
	OrderService orderService;
	RequestService requestService;

	OrderDTOValidator orderDTOValidator;

	@GetMapping
	public String waiterPage(Model model, @RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "1") Integer pageRoom,
			@RequestParam(required = false, defaultValue = "5") Integer size) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String login = ((UserDetails) principal).getUsername();
		model.addAttribute("userDTO", userService.findUserByLogin(login));
		Page<Room> freeRoomsPage = roomService.findAllFreeRoomsPaginated(pageRoom - 1, size);
		model.addAttribute("roomsCurrentPage", freeRoomsPage);
		model.addAttribute("roomsPageNumbers", Pagination.buildPageNumbers(freeRoomsPage.getTotalPages()));

		Page<Request> currentPage = requestService.findAllRequestsPageable(page - 1, size);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageNumbers", Pagination.buildPageNumbers(currentPage.getTotalPages()));

		return Path.WAITER_PAGE;
	}

	@GetMapping("/orders/edit/{id}")
	public String proceedRequestForm(@PathVariable Long id, Model model) {
		Request request = requestService.findRequestById(id);
		model.addAttribute(DTOHelper.toDTO(request));
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setCheckInDate(request.getCheckInDate());
		orderDTO.setCheckOutDate(request.getCheckOutDate());
		orderDTO.setUser(userService.findUserById(request.getUser().getId()));
		model.addAttribute(orderDTO);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long userId = ((AppUserDetails) principal).getId();
		String message = String.format("Waiter id %s going to proceed request id %s", userId, id);
		logger.info(message);

		return Path.WAITER_ORDER_EDIT_PAGE;
	}

	@PostMapping("/orders/{id}")
	public String proceedRequest(@PathVariable Long id, @ModelAttribute OrderDTO orderDTO, BindingResult errors,
			@ModelAttribute RequestDTO requestDTO, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long userId = ((AppUserDetails) principal).getId();
		orderDTO.setOrderDate(LocalDateTime.now());
		orderDTO.setOrderStatus(new OrderStatus());
		orderDTO.getOrderStatus().setId(2);
		orderDTOValidator.validate(orderDTO, errors);
		if (errors.hasErrors()) {
			model.addAttribute(orderDTO);
			String message = String.format("Waiter id %s failed to update order %s", userId, id);
			logger.info(message);
			return Path.WAITER_ORDER_EDIT_PAGE;
		}
		orderService.updateOrder(id, orderDTO);
		requestService.deleteRequest(id);

		String message = String.format("Waiter id %s updated order id %s", userId, id);
		logger.info(message);

		return Path.WAITER_PAGE_REDIRECT;
	}

}
