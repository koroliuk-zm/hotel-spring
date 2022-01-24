package hotel_spring.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.dkoroliuk.hotel_spring.dto.RoomDTO;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.entity.RoomType;
import com.dkoroliuk.hotel_spring.validators.RoomDTOValidator;

class RoomDTOValidatorTest {
private RoomDTO roomDTO;
private final RoomDTOValidator validator = new RoomDTOValidator();
private BindingResult errors;
@BeforeEach
void setUp() {
    roomDTO = RoomDTO
            .builder()
            .id(1L)
            .number(1)
            .seatsAmount(1)
            .perdayCost(1)
            .roomStatus(new RoomStatus(1, "free"))
            .roomType(new RoomType(1, "standart"))
            .description("description")
            .build();
    errors = new BeanPropertyBindingResult(roomDTO,"roomDTO");
}

@Test
void supportsWhenValidatorSupportsClassThenReturnTrue() {
    assertTrue(validator.supports(RoomDTO.class));
}

@Test
void supportsWhenValidatorNotSupportsClassThenReturnFalse() {
    assertFalse(validator.supports(Room.class));
}

@Test
void validateWhenNumberValidThenErrorsIsEmpty() {
    validator.validate(roomDTO, errors);
    assertFalse(errors.hasErrors());
}

@Test
void validateWhenNumberIsZeroOrLessThenErrorsHasNumberZeroOrLessErrorCode() {
    roomDTO.setNumber(-1);
    validator.validate(roomDTO, errors);
    assertTrue(errors.hasErrors());
    assertEquals("number.zeroOrLess", errors.getAllErrors().get(0).getCode());
}

@Test
void validateWhenSeatsAmountIsZeroOrLessThenErrorsHasSeatsAmountZeroOrLessErrorCode() {
    roomDTO.setSeatsAmount(-1);
    validator.validate(roomDTO, errors);
    assertTrue(errors.hasErrors());
    assertEquals("seatsAmount.zeroOrLess", errors.getAllErrors().get(0).getCode());
}

@Test
void validateWhenPerdayCostIsZeroOrLessThenErrorsHasPerdayCostZeroOrLessErrorCode() {
    roomDTO.setPerdayCost(-1);
    validator.validate(roomDTO, errors);
    assertTrue(errors.hasErrors());
    assertEquals("perdayCost.zeroOrLess", errors.getAllErrors().get(0).getCode());
}

@Test
void validateWhenDescriptionIsTooLongThenErrorsHasDescriptionTooLongErrorCode() {
	char[] chars = new char[1001];
	Arrays.fill(chars, '*');
	String text = new String(chars);
    roomDTO.setDescription(text);
    validator.validate(roomDTO, errors);
    assertTrue(errors.hasErrors());
    assertEquals("description.tooLong", errors.getAllErrors().get(0).getCode());
}

@Test
void validateWhenDescriptionIsBlankErrorsHasDescriptionBlankErrorCode() {
    roomDTO.setDescription("   ");
    validator.validate(roomDTO, errors);
    assertTrue(errors.hasErrors());
    assertEquals("description.isBlank", errors.getAllErrors().get(0).getCode());
}
}
