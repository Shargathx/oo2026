package ee.msaareva.proovikontrolltoo.controller;

import ee.msaareva.proovikontrolltoo.dto.FilmTypeDto;
import ee.msaareva.proovikontrolltoo.dto.RentalDto;
import ee.msaareva.proovikontrolltoo.entity.Film;
import ee.msaareva.proovikontrolltoo.entity.Rental;
import ee.msaareva.proovikontrolltoo.repository.FilmRepository;
import ee.msaareva.proovikontrolltoo.service.FilmService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor

public class FilmController {
    private final FilmRepository filmRepository;
    private final FilmService filmService;

    @GetMapping("films")
    public List<Film> getFilms() {
        return filmRepository.findAll();
    }

    @GetMapping("films/available")
    public List<Film> getAllFilmsAvailable() {
        return filmService.findAvailableFilms();
    }

    @PostMapping("film")
    public Film addFilm(@RequestBody Film film) {
        filmService.validateFilm(film);
        return filmRepository.save(film);
    }

    @PatchMapping("film/{id}/type")
    public Film updateFilm(@PathVariable Long id, @RequestBody FilmTypeDto filmTypeDto) {
        return filmService.updateFilmType(id, filmTypeDto.getFilmType());
    }

    @DeleteMapping("film/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmRepository.deleteById(id);
    }

}
