package com.dkoroliuk.hotel_spring.validators;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.dkoroliuk.hotel_spring.dto.OrderDTO;

@Service
public class OrderDTOValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return OrderDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		OrderDTO orderDTO = (OrderDTO) target;
		if(orderDTO.getOrderDate().toLocalDate().isAfter(orderDTO.getCheckInDate())) {
			errors.rejectValue("checkInDate", "checkInDate.earlier.orderDate");
		}
		if(orderDTO.getOrderDate().toLocalDate().isAfter(orderDTO.getCheckOutDate())) {
			errors.rejectValue("checkOutDate", "checkOutDate.earlier.orderDate");
		}
        if(orderDTO.getCheckInDate().isAfter(orderDTO.getCheckOutDate())){
        	errors.rejectValue("checkOutDate", "checkOutDate.earlier.checkInDate");
        }
        if(orderDTO.getTotalCost()<0) {
        	errors.rejectValue("totalCost", "totalCost.lessThanZero");
        }
        if(orderDTO.getRoom().getNumber()<1) {
        	errors.rejectValue("room.number", "roomNumber.ZeroOrLess");
        }
	}

}
