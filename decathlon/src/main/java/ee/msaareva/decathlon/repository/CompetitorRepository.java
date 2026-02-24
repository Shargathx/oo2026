package ee.msaareva.decathlon.repository;

import ee.msaareva.decathlon.entity.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitorRepository extends JpaRepository<Competitor, Long> {
    List<Competitor> findAllByName(String name);
}
