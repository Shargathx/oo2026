package ee.msaareva.kontrolltoo.repository;

import ee.msaareva.kontrolltoo.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
    boolean existsByWord(String word);
}
