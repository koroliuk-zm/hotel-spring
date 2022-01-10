package com.dkoroliuk.hotel_spring.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.service.UserService;
import com.dkoroliuk.hotel_spring.util.Path;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import static com.dkoroliuk.hotel_spring.util.Pagination.*;

@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	public static final String CURRENT_PAGE = "currentPage";
    public static final String PAGE_NUMBERS = "pageNumbers";
	private UserService userService;
	 /**
     * Method to access administrator page. Handles GET request for URL "/administrator"
     * @param model instance of {@link Model}
     * @return view "/administrator/admin_page".
     */
    @GetMapping
    public String adminPage(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = ((UserDetails) principal).getUsername();
        model.addAttribute("userDTO", userService.findUserByLogin(login));
        return Path.ADMIN_PAGE;
    }
    
    /**
     * Method to access all {@link User}s page. Handles GET request for URL "/administrator/users".
     * @param model implementation of {@link Model}
     * @param page number of requested page
     * @param size size of requested page
     * @return view "/administrator/all_users"
     */
    @GetMapping("/users")
    public String getAllUsers(Model model, @RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "5") Integer size) {
        Page<User> currentPage = userService.getAllUsersPageable(page-1, size);
        model.addAttribute(CURRENT_PAGE, currentPage);
        model.addAttribute(PAGE_NUMBERS, buildPageNumbers(currentPage.getTotalPages()));
        return Path.ADMIN_ALL_USERS_PAGE;
    }
}
