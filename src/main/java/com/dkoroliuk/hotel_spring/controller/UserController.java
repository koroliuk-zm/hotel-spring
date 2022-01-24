package com.dkoroliuk.hotel_spring.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.dkoroliuk.hotel_spring.dto.OrderDTO;
import com.dkoroliuk.hotel_spring.dto.RequestDTO;
import com.dkoroliuk.hotel_spring.entity.AppUserDetails;
import com.dkoroliuk.hotel_spring.entity.Order;
import com.dkoroliuk.hotel_spring.entity.OrderStatus;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.service.OrderService;
import com.dkoroliuk.hotel_spring.service.RequestService;
import com.dkoroliuk.hotel_spring.service.RoomService;
import com.dkoroliuk.hotel_spring.service.UserService;
import com.dkoroliuk.hotel_spring.util.Pagination;
import com.dkoroliuk.hotel_spring.util.Pagination.RoomPaginationParam;
import com.dkoroliuk.hotel_spring.util.Path;
import com.dkoroliuk.hotel_spring.validators.OrderDTOValidator;
import com.dkoroliuk.hotel_spring.validators.RequestDTOValidator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Controller for "USER" role. Handles all request starts with "/user"
 */
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	UserService userService;
	RoomService roomService;
	OrderService orderService;
	RequestService requestService;
	private OrderDTOValidator orderDTOValidator;
	private RequestDTOValidator requestDTOValidator;
	
	/**
     * Method to access {@link User} page. Handles GET request for URL "/user"
     * @param model instance of {@link Model}
     * @return view "/user/user_page"
     */
    @GetMapping
    public String userPage(Model model) {
        model.addAttribute("userDTO", userService.findUserById(getUserById()));
        model.addAttribute("roomOrders", orderService.findUsersOngoingOrders(getUserById()));
        model.addAttribute("ordersHistory", orderService.findUsersProcessedOrders(getUserById()));
        model.addAttribute("newOrders", requestService.findUsersUnhandledRequests(getUserById()));
        String message = String.format("User id %s access private cabinet", getUserById());
        logger.info(message);

        return Path.USER_PAGE;
    }

	private long getUserById() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((AppUserDetails) principal).getId();
	}
	
    /**
     * Method to access rooms page. Handles GET request for URL "/user/rooms"
     * @param model instance of {@link Model}
     * @param page number of requested page
     * @param size size of requested page
     * @param pagingParam instance of {@link Pagination.RoomPaginationParam}, which holds parameters for pagination
     * @return view "/user/rooms"
     */
    @GetMapping("/rooms")
    public String freeRoomsPage(Model model, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size,
                                     @ModelAttribute Optional<RoomPaginationParam> pagingParam) {
        RoomPaginationParam roomPaginationParam = pagingParam.orElse(new RoomPaginationParam());
        model.addAttribute("pagingParam", roomPaginationParam);
        int currentPage = page.orElse(1);
        int currentSize = size.orElse(5);
        Pageable pageable;
        if (roomPaginationParam.getSortOrder().equals("asc")) {
            pageable = PageRequest.of(currentPage - 1, currentSize, Sort.by(roomPaginationParam.getSortBy()));
        } else {
            pageable = PageRequest.of(currentPage - 1, currentSize, Sort.by(roomPaginationParam.getSortBy()).descending());
        }
        Page<Room> roomPage = roomService.findAllFreeRoomsPaginated(pageable);
        model.addAttribute("roomPage", roomPage);
        model.addAttribute("pageNumbers", Pagination.buildPageNumbers(roomPage.getTotalPages()));
        return Path.USER_ROOMS_PAGE;
    }
    
    /**
     * Method to access room ordering page. Handles GET request for URL "/user/rooms/order/{id}"
     * @param id {@link Room} id
     * @param model instance of {@link Model}
     * @return view "/user/create_room_order"
     */
    @GetMapping("/rooms/order/{id}")
    public String roomOrderPage(@PathVariable Long id, Model model) {
        OrderDTO roomOrderDTO = new OrderDTO();
        Room room = roomService.findRoomById(id);
        roomOrderDTO.setRoom(room);
        roomOrderDTO.setUser(userService.findUserById(getUserById()));
        model.addAttribute(roomOrderDTO);
        String message = String.format("User id %s going to order room number %s", getUserById(), roomOrderDTO.getRoom().getNumber());
        logger.info(message);
        return Path.USER_ROOM_ORDER_PAGE;
    }
    
    /**
     * Method to save new {@link Order}. Handles POST request for URL "/user/rooms/order"
     * @param roomOrderDTO model attribute {@link RoomOrderDTO} for entity {@link Order}
     * @return view "redirect:/user/rooms"
     */
    @PostMapping("/rooms/order")
    public String saveRoomOrder(@ModelAttribute OrderDTO roomOrderDTO, BindingResult errors, Model model) {
        roomOrderDTO.setOrderDate(LocalDateTime.now());
        roomOrderDTO.setOrderStatus(new OrderStatus());
        roomOrderDTO.getOrderStatus().setId(2);
        Room room = roomService.findRoomById(roomOrderDTO.getRoom().getId());
        roomOrderDTO.setRoom(room);
        orderDTOValidator.validate(roomOrderDTO, errors);
        if (errors.hasErrors()) {
            model.addAttribute(roomOrderDTO);
            String message = String.format("User id %s failed to create new order", getUserById());
            logger.info(message);
            return Path.USER_ROOM_ORDER_PAGE;
        }  
        orderService.saveRoomOrder(roomOrderDTO);
        String message = String.format("User id %s ordered room", getUserById());
        logger.info(message);
        return Path.USER_ROOMS_REDIRECT;
    }
    
    /**
     * Method to access page to make new order. Handles GET request for URL "/user/rooms/order/new_order"
     * @param model instance of {@link Model}
     * @return view "/user/create_new_order"
     */
    @GetMapping("/rooms/order/create_new_order/")
    public String newOrderPage(Model model) {
    	model.addAttribute(new RequestDTO());
    	String message = String.format("User id %s is going to create new request", getUserById());
        logger.info(message);
		return Path.USER_CREATE_ORDER_PAGE;
    }
    
    /**
     * Method to save new {@link Order}. Handles POST request for URL "/user/rooms"
     * @param roomOrderDTO model attribute {@link RoomOrderDTO} for entity {@link Order}
     * @return view "redirect:/user/rooms"
     */
    @PostMapping("/rooms/order/create_new_order/")
    public String saveNewRequest(@ModelAttribute RequestDTO requestDTO, BindingResult errors, Model model) {
    	requestDTO.setRequestDate(LocalDateTime.now());
        requestDTO.setUser(new User());
        requestDTO.getUser().setId(getUserById());
        requestDTOValidator.validate(requestDTO, errors);
        if (errors.hasErrors()) {
            model.addAttribute(requestDTO);
            String message = String.format("User id %s failed to create new request", getUserById());
            logger.info(message);
            return Path.USER_CREATE_ORDER_PAGE;
        }
        requestService.saveNewRequest(requestDTO);
        String message = String.format("User id %s made new order", getUserById());
        logger.info(message);
        return Path.USER_ROOMS_REDIRECT;
    }
    
    /**
     * Method to delete {@link Order} by id.  Handles POST request for URL ""/user/cancel_order/{id}".
     * Redirects to "/user".
     * @param id {@link Order} id
     * @return view "redirect:/user/user_page"
     */
    @PostMapping("/cancel_order/{id}")
    public String cancelOrder(@PathVariable Long id) {
    	long roomId = orderService.getRoomIdByOrder(id);
        orderService.deleteOrderById(id);
        roomService.changeRoomStatusToFree(roomId);
        String message = String.format("User id %s canceled order id %s", getUserById(), id);
        logger.info(message);
        return Path.REDIRECT_USER_PAGE;
    }
    
    /**
     * Method to delete {@link Order} by id.  Handles POST request for URL ""/user/cancel_order/{id}".
     * Redirects to "/user".
     * @param id {@link Order} id
     * @return view "redirect:/user/user_page"
     */
    @PostMapping("/pay_order/{id}")
    public String payOrder(@PathVariable Long id) {
    	long roomId = orderService.getRoomIdByOrder(id);
    	orderService.payOrder(id);
    	roomService.changeRoomStatusToBusy(roomId);
        String message = String.format("User id %s canceled order id %s", getUserById(), id);
        logger.info(message);
        return Path.REDIRECT_USER_PAGE;
    }
    
}
