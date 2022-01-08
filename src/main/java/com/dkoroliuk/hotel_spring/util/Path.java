package com.dkoroliuk.hotel_spring.util;

public class Path {
	public static final String WELCOME_PAGE = "/welcome";
	public static final String ERROR_PAGE = "error";
	public static final String LOGIN_PAGE = "/login";
	public static final String REGISTRATION_PAGE = "/registration";
	public static final String LOGIN_PAGE_REDIRECT = "redirect:/login";
	
	public static final String FORWARD_USER_REGISTRATION_PAGE = "/user/user_add_user";
	public static final String REDIRECT_USER_REGISTRATION_PAGE = "redirect:/userRegistration";
	
	public static final String REDIRECT_TO_VIEW_ALL_ROOMS = "redirect:/viewAllRooms";
	
	public static final String FORWARD_ADMIN_PROFILE = "/admin/admin_view_profile";
	public static final String FORWARD_USER_PROFILE = "/user/user_view_profile";
	public static final String REDIRECT_TO_PROFILE = "redirect:/viewProfile";
}
