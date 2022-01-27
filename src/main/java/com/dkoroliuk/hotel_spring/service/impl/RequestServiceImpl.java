package com.dkoroliuk.hotel_spring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dkoroliuk.hotel_spring.dto.RequestDTO;
import com.dkoroliuk.hotel_spring.entity.Request;
import com.dkoroliuk.hotel_spring.repository.RequestRepo;
import com.dkoroliuk.hotel_spring.service.RequestService;
import com.dkoroliuk.hotel_spring.util.DTOHelper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = { @Autowired })
@Service
public class RequestServiceImpl implements RequestService {
	RequestRepo requestRepo;

	@Override
	public RequestDTO saveNewRequest(RequestDTO requestDTO) {
		Request request = new Request();
		request.setRequestDate(requestDTO.getRequestDate());
		request.setCheckInDate(requestDTO.getCheckInDate());
		request.setCheckOutDate(requestDTO.getCheckOutDate());
		request.setSeatsNumber(requestDTO.getSeatsNumber());
		request.setRoomType(requestDTO.getRoomType());
		request.setUser(requestDTO.getUser());
		return DTOHelper.toDTO(requestRepo.save(request));
	}

	@Override
	public List<RequestDTO> findUsersRequests(long id) {
		return DTOHelper.requestListToDTO(requestRepo.findAllByUserId(id));
	}

	@Override
	public Page<Request> findAllRequestsPageable(int page, Integer size) {
		return requestRepo.findAll(PageRequest.of(page, size, Sort.by("requestDate")));
	}

	@Override
	public Request findRequestById(Long id) {
		return requestRepo.getById(id);
	}

	@Override
	public void deleteRequest(Long id) {
		requestRepo.deleteById(id);

	}
}
