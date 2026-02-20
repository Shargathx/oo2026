package ee.veateated.kodutoo_veateated.controller;

import ee.veateated.kodutoo_veateated.entity.Car;
import ee.veateated.kodutoo_veateated.repository.CarRepository;
import ee.veateated.kodutoo_veateated.service.CarService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarService carService;

    @GetMapping("cars")
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    @PostMapping("car")
    public Car addCar(@RequestBody Car car) {
        carService.validateCarDetails(car);
        return carRepository.save(car);
    }

    @DeleteMapping("cars/{id}")
    @Transactional
    public List<Car> deleteCars(@PathVariable Long id) {
        carRepository.deleteById(id);
        return carRepository.findAll();
    }
}