package hotel_spring.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.dkoroliuk.hotel_spring.dto.RequestDTO;
import com.dkoroliuk.hotel_spring.entity.Request;
import com.dkoroliuk.hotel_spring.entity.RoomType;
import com.dkoroliuk.hotel_spring.validators.RequestDTOValidator;

class RequestDTOValidatorTest {

	private RequestDTO requestDTO;
    private final RequestDTOValidator validator = new RequestDTOValidator();
    private BindingResult errors;

    @BeforeEach
    void setUp() {
        requestDTO = RequestDTO
                .builder()
                .id(1L)
                .requestDate(LocalDateTime.now())
                .checkInDate(LocalDate.now().plusDays(1))
                .checkOutDate(LocalDate.now().plusDays(2))
                .seatsNumber(1)
                .roomType(new RoomType(1, "standart"))
                .build();
        errors = new BeanPropertyBindingResult(requestDTO,"requestDTO");
    }
    
    @Test
    void supportsWhenValidatorSupportsClassThenReturnTrue() {
        assertTrue(validator.supports(RequestDTO.class));
    }

    @Test
    void supportsWhenValidatorNotSupportsClassThenReturnFalse() {
        assertFalse(validator.supports(Request.class));
    }
    
    @Test
    void validateWhenRequestDateIsLaterThanChecInDateThenErrorsHasCheckInDateEarlierRequestDateErrorCode() {
        requestDTO.setRequestDate(LocalDateTime.now());
        requestDTO.setCheckInDate(LocalDate.now().minusDays(1));
        validator.validate(requestDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("checkInDate.earlier.requestDate", errors.getAllErrors().get(0).getCode());
    }
    
    @Test
    void validateWhenRequestDateIsLaterThanChecOutDateThenThenErrorsHasCheckOutDateEarlierOrderDateErrorCode() {
        requestDTO.setRequestDate(LocalDateTime.now());
        requestDTO.setCheckOutDate(LocalDate.now().minusDays(1));
        validator.validate(requestDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("checkOutDate.earlier.requestDate", errors.getAllErrors().get(0).getCode());
    }
    
    @Test
    void validateWhenCheckInDateIsLaterThanCheckOutDateThenErrorsHasCheckOutDateEarlierCheckInDateErrorCode() {
        requestDTO.setCheckInDate(LocalDate.now().plusDays(2));
        requestDTO.setCheckOutDate(LocalDate.now().plusDays(1));
        validator.validate(requestDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("checkOutDate.earlier.checkInDate", errors.getAllErrors().get(0).getCode());
    }
    
    @Test
    void validateWhenSeatsNumberLessOrEqualsZeroThenErrorsHasSeatsNumberLessOrEqualsZeroErrorCode() {
        requestDTO.setSeatsNumber(0);
        validator.validate(requestDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("seatsNumber.lessOrEqualsZero", errors.getAllErrors().get(0).getCode());
    }
}
