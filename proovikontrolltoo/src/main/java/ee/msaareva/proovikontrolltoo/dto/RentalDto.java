package ee.msaareva.proovikontrolltoo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentalDto {
    private Long filmId;
    private LocalDate rentalDate;
    private Integer rentalDays;
}
