package ee.msaareva.proovikontrolltoo.service;

import ee.msaareva.proovikontrolltoo.dto.RentalDto;
import ee.msaareva.proovikontrolltoo.entity.Film;
import ee.msaareva.proovikontrolltoo.entity.FilmType;
import ee.msaareva.proovikontrolltoo.entity.Rental;
import ee.msaareva.proovikontrolltoo.repository.FilmRepository;
import ee.msaareva.proovikontrolltoo.repository.RentalRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final RentalRepository rentalRepository;

    public void validateFilm(Film film) {
        if (film.getTitle() == null || film.getTitle().isEmpty()) {
            throw new RuntimeException("Film title is empty");
        }
        if (film.getTitle().length() < 2 || film.getTitle().length() > 100) {
            throw new RuntimeException("Film title is invalid (too long or short)");
        }
        if (film.getFilmType() == null) {
            throw new RuntimeException("Film type is null");
        }
    }

    public ArrayList<Film> findAvailableFilms() {
        List<@NonNull Film> allFilms = filmRepository.findAll();
        List<Rental> activeRentals = rentalRepository.findByReturnDateNull();

        ArrayList<Film> availableFilms = new ArrayList<>();

        for (Film film : allFilms) {
            boolean isRented = false;
            for (Rental rental : activeRentals) {
                if (rental.getFilm().getId().equals(film.getId())) {
                    isRented = true;
                    break;
                }
            }
            if (!isRented) {
                availableFilms.add(film);
            }
        }
        return availableFilms;
    }

    public Film updateFilmType(Long id, FilmType filmtype) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Film not found"));
        if (filmtype == null) {
            throw new RuntimeException("Film type is null");
        }
        film.setFilmType(filmtype);
        return filmRepository.save(film);
    }

    public Rental startRental(RentalDto rentalDto) {
        @NonNull Film film = filmRepository.findById(rentalDto.getFilmId()).orElseThrow(() -> new RuntimeException("Film not found"));
        Rental rental = new Rental();
        rental.setFilm(film);
        rental.setRentDate(rentalDto.getRentalDate());
        rental.setPlannedDays(rentalDto.getRentalDays());
        return rentalRepository.save(rental);
    }


}
