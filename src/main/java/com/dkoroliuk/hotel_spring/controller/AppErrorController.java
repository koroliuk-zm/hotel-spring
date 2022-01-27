package com.dkoroliuk.hotel_spring.controller;

import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dkoroliuk.hotel_spring.util.Path;

/**
 * Controller to process errors
 */

@Controller
public class AppErrorController implements ErrorController {
	private static final Logger logger = LoggerFactory.getLogger(AppErrorController.class);

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Exception e) {
		String path = Path.ERROR_PAGE;
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());
			if (statusCode == HttpStatus.FORBIDDEN.value()) {
				logger.info("Attempt of unauthorized access");
				path = Path.BACK;
			} else if (statusCode == HttpStatus.NOT_FOUND.value()) {
				logger.info("Attempt of access to non existing resource");
				path = Path.ERROR_404_PAGE;
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				logger.error("Request " + request.getRequestURL() + " raised ", e);
			}
		}
		return path;
	}

	@RequestMapping("/back")
	public String back() {
		String path = Path.INDEX_PAGE_REDIRECT;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal == null) {
			return path;
		}
		Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();
		String authority = authorities.toString();
		if (authority.contains("ROLE_ADMIN")) {
			path = Path.ADMIN_PAGE_REDIRECT;
		} else if (authority.contains("ROLE_WAITER")) {
			path = Path.WAITER_PAGE_REDIRECT;
		} else if (authority.contains("ROLE_USER")) {
			path = Path.USER_ROOMS_REDIRECT;
		}
		return path;
	}
}
