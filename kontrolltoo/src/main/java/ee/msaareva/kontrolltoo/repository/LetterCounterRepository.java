package ee.msaareva.kontrolltoo.repository;

import ee.msaareva.kontrolltoo.entity.LetterCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterCounterRepository extends JpaRepository<LetterCounter, Long> {
}
