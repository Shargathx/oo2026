package ee.msaareva.proovikontrolltoo.dto;

import ee.msaareva.proovikontrolltoo.entity.FilmType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmTypeDto {
    @NotNull
    private FilmType filmType;
}
