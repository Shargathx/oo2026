package ee.msaareva.proovikontrolltoo.repository;

import ee.msaareva.proovikontrolltoo.entity.Film;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<@NonNull Film, @NonNull Long> {
}
