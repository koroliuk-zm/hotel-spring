package com.dkoroliuk.hotel_spring.controller;

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

import com.dkoroliuk.hotel_spring.dto.UserDTO;
import com.dkoroliuk.hotel_spring.entity.AppUserDetails;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.service.UserService;
import com.dkoroliuk.hotel_spring.util.DTOHelper;
import com.dkoroliuk.hotel_spring.util.Pagination;
import com.dkoroliuk.hotel_spring.util.Path;
import com.dkoroliuk.hotel_spring.validators.UserDTOValidator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	public static final String CURRENT_PAGE = "currentPage";
    public static final String PAGE_NUMBERS = "pageNumbers";
	private UserService userService;
	private UserDTOValidator userDTOValidator;
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
        model.addAttribute(PAGE_NUMBERS, Pagination.buildPageNumbers(currentPage.getTotalPages()));
        return Path.ADMIN_ALL_USERS_PAGE;
    }
    
    @GetMapping("/users/new_user")
    public String getUserFormForAdmin(Model model) {
    	model.addAttribute(new UserDTO());
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	String login = ((UserDetails) principal).getUsername();
    	String message = String.format("Administrator login %s is going to add new user", login);
        logger.info(message);
		return Path.ADMIN_USER_FORM;
    }
    
    /**
     * Method to add new {@link User}. Handles POST request for URL "/admin/users".
     * If userDTO is valid, then redirects to "redirect:/admin/users",
     * otherwise forwards to "/admin/create_user"
     * @param userDTO model attribute {@link UserDTO} for entity {@link User}
     * @param errors instance of {@link Model}
     * @param model instance of {@link BindingResult}
     * @return view
     */
    @PostMapping("/users")
    public String addNewUser(@ModelAttribute UserDTO userDTO, BindingResult errors, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = ((AppUserDetails) principal).getUsername();

        userDTOValidator.validate(userDTO, errors);
        if (errors.hasErrors()) {
            model.addAttribute(userDTO);

            String message = String.format("Administrator login %s failed to add new user", login);
            logger.info(message);

            return Path.ADMIN_USER_FORM;
        }

        User user = userService.saveUser(userDTO);

        String message = String.format("Administrator login %s added new user login %s", login, user.getLogin());
        logger.info(message);

        return Path.REDIRECT_ADMIN_ALL_USERS_PAGE;
    }
    
    /**
     * Method to access {@link User} edit page. Handles GET request for URL "/administrator/users/edit/{id}".
     * @param id {@link User} id
     * @param model instance of {@link Model}
     * @return view "/administrator/edit_user"
     */
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute(DTOHelper.toDTO(userService.findUserById(id)));

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = ((AppUserDetails) principal).getUsername();
        String message = String.format("Administrator login %s is going to update user id %s", login, id);
        logger.info(message);

        return Path.EDIT_USER_PAGE;
    }
    
    /**
     * Method to update {@link User} by id. Handles POST request for URL "/admin/users/{id}".
     * If userDTO is valid, then redirects to "redirect:/admin/users",
     * otherwise forwards to "/admin/edit_user"
     * @param id {@link User} id
     * @param userDTO model attribute {@link UserDTO} for entity {@link User}
     * @param errors instance of {@link Model}
     * @param model instance of {@link BindingResult}
     * @return view
     */
    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDTO userDTO, BindingResult errors, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = ((AppUserDetails) principal).getUsername();

        userDTOValidator.validate(userDTO, errors);
        if (errors.hasErrors()) {
            model.addAttribute(userDTO);

            String message = String.format("Administrator login %s failed to update user id %s", login, id);
            logger.info(message);

            return Path.EDIT_USER_PAGE;
        }

        userService.updateUser(id, userDTO);

        String message = String.format("Administrator login %s updated user id %s", login, id);
        logger.info(message);

        return Path.REDIRECT_ADMIN_ALL_USERS_PAGE;
    }
    
    /**
     * Method to delete {@link User} by id. Handles POST request for URL "/administrator/users/delete/{id}".
     * @param id {@link User} id
     * @return view "redirect:/administrator/users"
     */
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = ((AppUserDetails) principal).getUsername();
        String message = String.format("Administrator login %s deleted user id %s", login, id);
        logger.info(message);

        return Path.REDIRECT_ADMIN_ALL_USERS_PAGE;
    }
}
