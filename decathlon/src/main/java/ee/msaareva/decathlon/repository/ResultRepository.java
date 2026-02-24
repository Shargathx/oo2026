package ee.msaareva.decathlon.repository;

import ee.msaareva.decathlon.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findAllById(Long id);

    List<Result> findAllByCompetitorId(Long id);
}
