package ee.veateated.kodutoo_veateated.repository;

import ee.veateated.kodutoo_veateated.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> id(Long id);

    void deleteById(Long id);
}
