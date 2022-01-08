package com.dkoroliuk.hotel_spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dkoroliuk.hotel_spring.dto.UserDTO;
import com.dkoroliuk.hotel_spring.entity.UserRole;
import com.dkoroliuk.hotel_spring.service.UserService;
import com.dkoroliuk.hotel_spring.util.Path;
import com.dkoroliuk.hotel_spring.validators.UserDTOValidator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * Controller for guest.
 */
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private UserService userService;
    private UserDTOValidator userDTOValidator;

    /**
     * Method to access index page. Handles GET request for URL "/"
     * @param model instance of {@link Model}
     * @param page number of requested page
     * @param size size of requested page
     * @param pagingParam instance of {@link BookPaginationParam}, which holds parameters for pagination
     * @return view "/index"
     */
    @GetMapping
    public String indexPage() {
        return Path.WELCOME_PAGE;
    }

    /**
     * Method to access login page. Handles GET request for URL "/login"
     * @return view "/login"
     */
    @GetMapping("/login")
    public String loginPage() {
        return Path.LOGIN_PAGE;
    }

    /**
     * Method to access registration page. Handles GET request for URL "/registration"
     * @param model instance of {@link Model}
     * @return view "/registration"
     */
    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute(new UserDTO());
        return Path.REGISTRATION_PAGE;
    }

    /**
     * Method for user registration. Handles POST request for URL "/registration".
     * If userDTO is valid, then redirects to "redirect:/login",
     * otherwise forwards to "/registration"
     * @param userDTO model attribute {@link UserDTO} for entity {@link User}
     * @param errors instance of {@link Model}
     * @param model instance of {@link BindingResult}
     * @return view
     */
    @PostMapping("/registration")
    public String registerNewUser(@ModelAttribute UserDTO userDTO, BindingResult errors, Model model) {
        userDTOValidator.validate(userDTO, errors);
        if (userService.findUserByLogin(userDTO.getLogin()) != null) {
            model.addAttribute("userExist", true);
            model.addAttribute(userDTO);
            String message = String.format("Registration failed for user with login \"%s\", this login already in use!", userDTO.getLogin());
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
}
