package com.dkoroliuk.hotel_spring.validators;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.dkoroliuk.hotel_spring.dto.UserDTO;

@Service
public class UserDTOValidator implements Validator {
    private static final String LOGIN_REGEX = "^[A-Za-z0-9]{1,20}$";
    private static final String PASSWORD_REGEX = "^[A-Za-z0-9]{6,}$";
    private static final String EMAIL_REGEX = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    /**
     * Method to check if given class supports by this validator
     * @param clazz class to be checked
     * @return true if supports, false - otherwise
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.isAssignableFrom(clazz);
    }

    /**
     * Validation method of {@link UserDTO} object
     * @param target object to be validated
     * @param errors instance of {@link Errors}
     */
    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "surname.empty");
        UserDTO userDTO = (UserDTO) target;
        if (!userDTO.getLogin().matches(LOGIN_REGEX)) {
            errors.rejectValue("login","login.regex");
        }
        if (userDTO.getId() == 0) {
            validatePasswords(errors, userDTO);
        } else {
            if (!userDTO.getPassword().isEmpty()) {
                validatePasswords(errors, userDTO);
            }
        }
        if (!userDTO.getEmail().matches(EMAIL_REGEX)) {
            errors.rejectValue("email", "email.regex");
        }
    }

    private void validatePasswords(Errors errors, UserDTO userDTO) {
        if (!userDTO.getPassword().matches(PASSWORD_REGEX)) {
            errors.rejectValue("password", "password.regex");
        }
        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", "password.equals");
        }
    }
}

