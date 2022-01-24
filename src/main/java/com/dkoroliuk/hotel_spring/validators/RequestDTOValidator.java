package com.dkoroliuk.hotel_spring.validators;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.dkoroliuk.hotel_spring.dto.RequestDTO;

@Service
public class RequestDTOValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		return RequestDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RequestDTO requestDTO = (RequestDTO) target;
		if(requestDTO.getRequestDate().toLocalDate().isAfter(requestDTO.getCheckInDate())) {
			errors.rejectValue("checkInDate", "checkInDate.earlier.requestDate");
		}
		if(requestDTO.getRequestDate().toLocalDate().isAfter(requestDTO.getCheckOutDate())) {
			errors.rejectValue("checkOutDate", "checkOutDate.earlier.requestDate");
		}
        if(requestDTO.getCheckInDate().isAfter(requestDTO.getCheckOutDate())){
        	errors.rejectValue("checkOutDate", "checkOutDate.earlier.checkInDate");
        }
        if(requestDTO.getSeatsNumber()<=0) {
        	errors.rejectValue("seatsNumber", "seatsNumber.lessOrEqualsZero");
        }
	}
}
