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
	public static final String ADMIN_PAGE_REDIRECT = "redirect:/admin";
	public static final String WAITER_PAGE_REDIRECT = "redirect:/waiter";
	public static final String USER_ROOMS_REDIRECT = "redirect:/user";
	public static final String ADMIN_PAGE = "/admin/admin_page";
	public static final String ADMIN_ALL_USERS_PAGE = "/admin/all_users";
	public static final String ADMIN_USER_FORM = "/admin/create_user";
	public static final String REDIRECT_ADMIN_ALL_USERS_PAGE = "redirect:/admin/users";
	public static final String EDIT_USER_PAGE = "/admin/edit_user";
	public static final String ADMIN_ALL_ROOMS_PAGE = "/admin/all_rooms";
	public static final String ADMIN_ROOM_FORM = "/admin/create_room";
	public static final String REDIRECT_ADMIN_ALL_ROOMS_PAGE = "redirect:/admin/rooms";
	public static final String EDIT_ROOM_PAGE = "/admin/edit_room";
	public static final String USER_PAGE = "/user/user_page";
	public static final String USER_ROOMS_PAGE = "/user/rooms";
	public static final String USER_ROOM_ORDER_PAGE = "/user/create_room_order";
	public static final String WAITER_PAGE = "/waiter/waiter_page";
	public static final String USER_CREATE_ORDER_PAGE = "/user/create_new_order";
	public static final String WAITER_ORDER_EDIT_PAGE = "/waiter/edit_order";
	public static final String REDIRECT_USER_PAGE = "redirect:/user";
	
}
