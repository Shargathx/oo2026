package ee.msaareva.proovikontrolltoo.service;

import ee.msaareva.proovikontrolltoo.dto.RentalDto;
import ee.msaareva.proovikontrolltoo.entity.Film;
import ee.msaareva.proovikontrolltoo.entity.Rental;
import ee.msaareva.proovikontrolltoo.repository.FilmRepository;
import ee.msaareva.proovikontrolltoo.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static ee.msaareva.proovikontrolltoo.entity.Price.BASIC_PRICE;
import static ee.msaareva.proovikontrolltoo.entity.Price.PREMIUM_PRICE;

@Service
@AllArgsConstructor
public class RentalService {
    private FilmRepository filmRepository;
    private RentalRepository rentalRepository;

    public Rental startRental(RentalDto rentalDto) {
        Film film = filmRepository.findById(rentalDto.getFilmId()).orElseThrow(() -> new RuntimeException("Film not found"));
        Rental rental = new Rental();
        rental.setFilm(film);
        rental.setRentDate(rentalDto.getRentalDate());
        rental.setPlannedDays(rentalDto.getRentalDays());

        double sum = 0;
        int days = rentalDto.getRentalDays();

        switch (film.getFilmType()) {
            case NEW_RELEASE: {
                sum += PREMIUM_PRICE.getPrice() * days;
                break;
            }

            case REGULAR_RELEASE: {
                if (days <= 3) {
                    sum += BASIC_PRICE.getPrice() * days;
                } else {
                    sum += BASIC_PRICE.getPrice() * 3 + BASIC_PRICE.getPrice() * (days - 3);
                }
                break;
            }
            case OLD_RELEASE: {
                if (days <= 5) {
                    sum += BASIC_PRICE.getPrice() * days;
                } else {
                    sum += BASIC_PRICE.getPrice() * 5 + BASIC_PRICE.getPrice() * (days - 5);
                }
                break;
            }
        }
        rental.setTotalPrice(sum);
        return rentalRepository.save(rental);
    }

    public void endRental(RentalDto rentalDto) {

    }
}
