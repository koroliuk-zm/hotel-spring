package com.dkoroliuk.hotel_spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.dkoroliuk.hotel_spring.entity.Request;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.RoomType;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.entity.UserRole;
import com.dkoroliuk.hotel_spring.repository.RequestRepo;
import com.dkoroliuk.hotel_spring.service.impl.RequestServiceImpl;
import com.dkoroliuk.hotel_spring.util.DTOHelper;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {
	private static final Request REQUEST = new Request(1L, LocalDateTime.of(2022, 4, 22, 15, 52),
			LocalDate.of(2022, 4, 22), LocalDate.of(2022, 4, 22), 1,
			new User(1L, "name", "surname", "login", "password", "email", new UserRole(3, "user"), true),
			new RoomType(1, "standart"));
	@Mock
	private RequestRepo requestRepoMock;
	@InjectMocks
	private RequestServiceImpl service;

	@Test
	void testSaveNewRequest() {
		ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
		when(requestRepoMock.save(any(Request.class))).thenReturn(REQUEST);
		service.saveNewRequest(DTOHelper.toDTO(REQUEST));
		verify(requestRepoMock).save(requestCaptor.capture());

		assertEquals(1, requestCaptor.getValue().getSeatsNumber());
		assertEquals(LocalDateTime.of(2022, 4, 22, 15, 52), requestCaptor.getValue().getRequestDate());
		assertEquals(LocalDate.of(2022, 4, 22), requestCaptor.getValue().getCheckInDate());
		assertEquals(LocalDate.of(2022, 4, 22), requestCaptor.getValue().getCheckOutDate());
		assertEquals(new UserRole(3, "user"), requestCaptor.getValue().getUser().getUserRole());
		assertEquals(new RoomType(1, "standart"), requestCaptor.getValue().getRoomType());
	}

	@Test
	void testFindUsersRequests() {
		when(requestRepoMock.findAllByUserId(anyLong())).thenReturn(Arrays.asList(REQUEST));
		assertEquals(DTOHelper.toDTO(REQUEST), service.findUsersRequests(anyLong()).get(0));
	}

	@Test
	void testFindAllRequestsPageable() {
		Page<Request> page = mock(Page.class);
		when(requestRepoMock.findAll(PageRequest.of(1, 5, Sort.by("requestDate")))).thenReturn(page);
		assertEquals(page, service.findAllRequestsPageable(1, 5));
	}

	@Test
	void testFindRequestById() {
		when(requestRepoMock.getById(anyLong())).thenReturn(REQUEST);
		assertEquals(REQUEST, service.findRequestById(anyLong()));
	}
	
	@Test
	void testDeleteRequest() {
		service.deleteRequest(anyLong());
		verify(requestRepoMock, times(1)).deleteById(anyLong());
	}
}
