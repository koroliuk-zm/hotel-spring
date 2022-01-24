package com.dkoroliuk.hotel_spring.validators;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.dkoroliuk.hotel_spring.dto.RoomDTO;

@Service
public class RoomDTOValidator implements Validator{
	/**
     * Method to check if given class supports by this validator
     * @param clazz class to be checked
     * @return true if supports, false - otherwise
     */
	@Override
	public boolean supports(Class<?> clazz) {
		return RoomDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	        RoomDTO roomDTO = (RoomDTO) target;
	        if (roomDTO.getNumber() <= 0) {
	            errors.rejectValue("number", "number.zeroOrLess");
	        }
	        if (roomDTO.getSeatsAmount() <= 0) {
	            errors.rejectValue("seatsAmount", "seatsAmount.zeroOrLess");
	        }
	        if (roomDTO.getPerdayCost() <= 0) {
	            errors.rejectValue("perdayCost", "perdayCost.zeroOrLess");
	        }
	        if (roomDTO.getDescription().length() > 1000) {
	            errors.rejectValue("description", "description.tooLong");
	        }
	        if (isBlankString(roomDTO.getDescription())) {
	            errors.rejectValue("description", "description.isBlank");
	        }      
	}
	
    public boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }

}
