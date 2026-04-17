package ee.msaareva.decathlon.service;

import ee.msaareva.decathlon.entity.Competitor;
import ee.msaareva.decathlon.entity.Discipline;
import ee.msaareva.decathlon.entity.Result;
import ee.msaareva.decathlon.entity.ResultDto;
import ee.msaareva.decathlon.repository.CompetitorRepository;
import ee.msaareva.decathlon.repository.ResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class DecathlonService {

    private final CompetitorRepository competitorRepository;
    private final ResultRepository resultRepository;

    public Result addResultToCompetitor(Long competitorId, Result result) {
        Competitor competitor = competitorRepository.findById(competitorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competitor not found!"));
        if (result.getValue() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Value must not be null");
        }
        result.setCompetitor(competitor);
        result.setScore(calculateScore(result.getDiscipline(), result.getValue()));
        return resultRepository.save(result);
    }

    private double calculateScore(Discipline discipline, double value) {
        double score = 0;
        if (discipline == Discipline.HUNDRED_M) {
            score = value * 1.5;
        } else if (discipline == Discipline.LONG_JUMP) {
            score = value * 2.5;
        }
        return score;
    }

    public Page<ResultDto> getResults(Pageable pageable, String country) {

        Page<Result> resultPage;

        if (country != null) {
            resultPage = resultRepository.findByCompetitorCountry(country, pageable);
        } else {
            resultPage = resultRepository.findAll(pageable);
        }

        return resultPage.map(result -> {
            ResultDto dto = new ResultDto();
            dto.setId(result.getId());
            dto.setDiscipline(result.getDiscipline().name());
            dto.setValue(result.getValue());
            dto.setScore(result.getScore());
            dto.setCompetitorId(result.getCompetitor().getId());
            return dto;
        });
    }
}

