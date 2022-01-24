package hotel_spring.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.dkoroliuk.hotel_spring.dto.UserDTO;
import com.dkoroliuk.hotel_spring.entity.User;
import com.dkoroliuk.hotel_spring.validators.UserDTOValidator;

class UserDTOValidatorTest {
	private UserDTO userDTO;
    private final UserDTOValidator validator = new UserDTOValidator();
    private BindingResult errors;

    @BeforeEach
    void setUp() {
        userDTO = UserDTO
                .builder()
                .id(1L)
                .login("user")
                .name("user")
                .surname("user")
                .email("user@user.com")
                .password("123456")
                .passwordConfirm("123456")
                .build();
        errors = new BeanPropertyBindingResult(userDTO,"userDTO");
    }

    @Test
    void supportsWhenValidatorSupportsClassThenReturnTrue() {
        assertTrue(validator.supports(UserDTO.class));
    }

    @Test
    void supportsWhenValidatorNotSupportsClassThenReturnFalse() {
        assertFalse(validator.supports(User.class));
    }

    @Test
    void validateWhenUserDTOIsValidThenErrorsIsEmpty() {
        validator.validate(userDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    void validateWhenNameNotValidThenErrorsHasNameEmptyErrorCode() {
        userDTO.setName(" ");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("name.empty", errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateWhenSurnameNotValidThenErrorsHasSurnameEmptyErrorCode() {
        userDTO.setSurname(" ");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("surname.empty", errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateWhenLoginNotValidThenErrorsHasLoginRegexErrorCode() {
        userDTO.setLogin(" ");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("login.regex", errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateWhenUserIsNewAndNotValidPasswordThenErrorsHasPasswordRegexErrorCode() {
        userDTO.setId(0);
        userDTO.setPassword(" ");
        userDTO.setPasswordConfirm(" ");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("password.regex", errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateWhenUserIsNewAndPasswordsNotEqualsThenErrorsHasPasswordEqualsErrorCode() {
        userDTO.setId(0);
        userDTO.setPassword("321321321321");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("password.equals", errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateWhenUserNotNewAndNotValidPasswordThenErrorsHasLoginRegexErrorCode() {
        userDTO.setPassword(" ");
        userDTO.setPasswordConfirm(" ");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("password.regex", errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateWhenUserNotNewAndPasswordsNotEqualsThenErrorsHasPasswordEqualsErrorCode() {
        userDTO.setPassword("321321321321");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("password.equals", errors.getAllErrors().get(0).getCode());
    }

    @Test
    void validateWhenUserNotNewAndEmailNotValidThenErrorsHasNameEmptyErrorCode() {
        userDTO.setEmail(" ");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("email.regex", errors.getAllErrors().get(0).getCode());
    }
}
