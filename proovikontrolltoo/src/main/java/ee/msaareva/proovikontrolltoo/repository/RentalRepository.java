package ee.msaareva.proovikontrolltoo.repository;

import ee.msaareva.proovikontrolltoo.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByReturnDateNull();
}
