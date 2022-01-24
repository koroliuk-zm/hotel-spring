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

import com.dkoroliuk.hotel_spring.dto.OrderDTO;
import com.dkoroliuk.hotel_spring.entity.Order;
import com.dkoroliuk.hotel_spring.entity.OrderStatus;
import com.dkoroliuk.hotel_spring.entity.Room;
import com.dkoroliuk.hotel_spring.entity.RoomStatus;
import com.dkoroliuk.hotel_spring.entity.RoomType;
import com.dkoroliuk.hotel_spring.validators.OrderDTOValidator;

class OrderDTOValidatorTest {
	private OrderDTO orderDTO;
    private final OrderDTOValidator validator = new OrderDTOValidator();
    private BindingResult errors;

    @BeforeEach
    void setUp() {
        orderDTO = OrderDTO
                .builder()
                .id(1L)
                .orderDate(LocalDateTime.now())
                .checkInDate(LocalDate.now().plusDays(1))
                .checkOutDate(LocalDate.now().plusDays(2))
                .totalCost(100)
                .orderStatus(new OrderStatus(1, "new"))
                .room(new Room(1, 1, 1, 1, new RoomStatus(1, "free"), new RoomType(1, "standart"), "desc"))
                .build();
        errors = new BeanPropertyBindingResult(orderDTO,"orderDTO");
    }
    
    @Test
    void supportsWhenValidatorSupportsClassThenReturnTrue() {
        assertTrue(validator.supports(OrderDTO.class));
    }

    @Test
    void supportsWhenValidatorNotSupportsClassThenReturnFalse() {
        assertFalse(validator.supports(Order.class));
    }
    
    @Test
    void validateWhenOrderDateIsLaterThanChecInDateThenThenErrorsHasCheckInDateEarlierOrderDateErrorCode() {
        orderDTO.setOrderDate(LocalDateTime.now());
        orderDTO.setCheckInDate(LocalDate.now().minusDays(1));
        validator.validate(orderDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("checkInDate.earlier.orderDate", errors.getAllErrors().get(0).getCode());
    }
    
    @Test
    void validateWhenOrderDateIsLaterThanChecOutDateThenThenErrorsHasCheckOutDateEarlierOrderDateErrorCode() {
        orderDTO.setOrderDate(LocalDateTime.now());
        orderDTO.setCheckOutDate(LocalDate.now().minusDays(1));
        validator.validate(orderDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("checkOutDate.earlier.orderDate", errors.getAllErrors().get(0).getCode());
    }
    
    @Test
    void validateWhenCheckInDateIsLaterThanCheckOutDateThenErrorsHasCheckOutDateEarlierCheckInDateErrorCode() {
        orderDTO.setCheckInDate(LocalDate.now().plusDays(2));
        orderDTO.setCheckOutDate(LocalDate.now().plusDays(1));
        validator.validate(orderDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("checkOutDate.earlier.checkInDate", errors.getAllErrors().get(0).getCode());
    }
    
    @Test
    void validateWhenTotalCostIsLessThanZeroThenErrorsHasNumberZeroOrLessErrorCode() {
        orderDTO.setTotalCost(-1);
        validator.validate(orderDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("totalCost.lessThanZero", errors.getAllErrors().get(0).getCode());
    }
    
    @Test
    void validateWhenRoomNumberIsZeroOrLessThenErrorsHasRoomNumberZeroOrLessErrorCode() {
        orderDTO.setRoom(new Room());
        orderDTO.getRoom().setNumber(-1);
        validator.validate(orderDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals("roomNumber.ZeroOrLess", errors.getAllErrors().get(0).getCode());
    }
}
