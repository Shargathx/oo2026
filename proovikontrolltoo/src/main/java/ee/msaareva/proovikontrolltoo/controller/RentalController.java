package ee.msaareva.proovikontrolltoo.controller;

import ee.msaareva.proovikontrolltoo.dto.RentalDto;
import ee.msaareva.proovikontrolltoo.entity.Rental;
import ee.msaareva.proovikontrolltoo.repository.FilmRepository;
import ee.msaareva.proovikontrolltoo.repository.RentalRepository;
import ee.msaareva.proovikontrolltoo.service.FilmService;
import ee.msaareva.proovikontrolltoo.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RentalController {
    private final FilmService filmService;
    private final RentalRepository rentalRepository;
    private final FilmRepository filmRepository;
    private final RentalService rentalService;

    @GetMapping("rental")
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    // mitmes päevaks võtan
    @PostMapping("start-rental")
    public Rental startRental(@RequestBody RentalDto rentalDto) {
        return rentalService.startRental(rentalDto);
    }

//    // mitu päeva tegelt rendis oli, tee check vastavalt
//    @PostMapping("end-rental")
//    public Rental endRental(@RequestBody RentalDto rentalDto) {
//
//    }
}
