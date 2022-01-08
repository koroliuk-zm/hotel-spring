package com.dkoroliuk.hotel_spring.util;

public class Path {
	private Path() {
	}
	public static final String WELCOME_PAGE = "/index";
	public static final String ERROR_PAGE = "/error";
	public static final String LOGIN_PAGE = "/login";
	public static final String REGISTRATION_PAGE = "/registration";
	public static final String LOGIN_PAGE_REDIRECT = "redirect:/login";
	public static final String BACK = "forward:/back";
	public static final String ERROR_404_PAGE = "/error_404";
	public static final String INDEX_PAGE_REDIRECT = "redirect:/";
	
	public static final String FORWARD_USER_REGISTRATION_PAGE = "/user/user_add_user";
	public static final String REDIRECT_USER_REGISTRATION_PAGE = "redirect:/userRegistration";
	
	public static final String REDIRECT_TO_VIEW_ALL_ROOMS = "redirect:/viewAllRooms";
	
	public static final String FORWARD_ADMIN_PROFILE = "/admin/admin_view_profile";
	public static final String FORWARD_USER_PROFILE = "/user/user_view_profile";
	public static final String REDIRECT_TO_PROFILE = "redirect:/viewProfile";
	public static final String ADMIN_PAGE_REDIRECT = "redirect:/administrator";
	public static final String WAITER_PAGE_REDIRECT = "redirect:/waiter";
	public static final String USER_ROOMS_REDIRECT = "redirect:/user/rooms";
}
