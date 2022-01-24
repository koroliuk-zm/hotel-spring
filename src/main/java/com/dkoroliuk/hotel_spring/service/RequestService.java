package com.dkoroliuk.hotel_spring.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dkoroliuk.hotel_spring.dto.RequestDTO;
import com.dkoroliuk.hotel_spring.entity.Request;

public interface RequestService {
	RequestDTO saveNewRequest (RequestDTO requestDTO);

	List<RequestDTO> findUsersUnhandledRequests(long userById);

	Page<Request> findNewUnhandledRequestsPageable(int page, Integer size);

	Request findRequestById(Long id);

	void deleteRequest(Long id);
}
