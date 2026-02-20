package ee.veateated.kodutoo_veateated.service;

import ee.veateated.kodutoo_veateated.entity.Car;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    public void validateCarDetails(Car car) {
        if (car.getModel() == null || car.getModel().isEmpty()) {
            throw new RuntimeException("Field 'Car model' cannot be empty / null");
        }
        if (car.getColor() == null || car.getColor().isEmpty()) {
            throw new RuntimeException("Field 'Car color' cannot be empty / null");
        }
        if (car.getYear() < 1886 || car.getYear() > 2026) { // peaks ka kontrollima, et poleks tühi väli, aga sama probleem, mis allpool
            throw new RuntimeException("Car year cannot be less that 1886 or larger than 2026");
        }
        if (car.getKilometers() == null || car.getKilometers().isEmpty()) {
            throw new RuntimeException("Field 'Car kilometers' cannot be empty / null");
        }
        if (car.getPrice() == null || car.getPrice().isEmpty()) {
            throw new RuntimeException("Field 'Car price' cannot be empty / null");
        }
        /*
         Peaks ka kontrollima, et hind poleks negatiivne, aga see läheb teadmistest väljaspoole,
         tuleks hakata parsima Double jnejne. Ala (GPT):
        try {
            if (Double.parseDouble(car.getPrice()) < 0) {
                throw new RuntimeException("Car price cannot be less than 0");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Car price must be a valid number");
        }
         */
        if (car.getVin() == null || car.getVin().isEmpty()) {
            throw new RuntimeException("Field 'Car vin' cannot be empty / null");
        }
    }
}