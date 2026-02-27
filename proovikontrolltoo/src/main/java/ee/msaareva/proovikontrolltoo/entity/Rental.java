package ee.msaareva.proovikontrolltoo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private Integer plannedDays;
    private Double totalPrice;

    @ManyToOne
    Film film;
}
