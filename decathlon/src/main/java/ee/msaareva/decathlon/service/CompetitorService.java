package ee.msaareva.decathlon.service;

import ee.msaareva.decathlon.entity.Competitor;
import ee.msaareva.decathlon.entity.Result;
import ee.msaareva.decathlon.entity.ResultDto;
import ee.msaareva.decathlon.repository.CompetitorRepository;
import ee.msaareva.decathlon.repository.ResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CompetitorService {


    private final CompetitorRepository competitorRepository;
    private final ResultRepository resultRepository;

    public Competitor validateCompetitor(Competitor competitor) {
        if (competitor.getName() == null || competitor.getName().isEmpty()) {
            throw new RuntimeException("Competitor name cannot be null or empty");
        }
        if (!competitorRepository.findAllByName(competitor.getName()).isEmpty()) {
            throw new RuntimeException("Competitor with that name already exists");
        }
        if (competitor.getAge() < 12 || competitor.getAge() > 80) {
            throw new RuntimeException("Competitor age cannot be negative or above 80");
        }
        if (competitor.getCountry() == null || competitor.getCountry().isEmpty()) {
            throw new RuntimeException("Competitor country cannot be null or empty");
        }
        if (competitor.getGender() == null || competitor.getGender().isEmpty()) {
            throw new RuntimeException("Competitor gender cannot be null or empty");
        }
        return competitor;
    }

    public List<ResultDto> getAllResults() {
        List<Result> results = resultRepository.findAll();
        List<ResultDto> resultDtos = new ArrayList<>();

        ResultDto resultDto = null;
        for (Result result : results) {
            resultDto = new ResultDto();
            resultDto.setId(result.getId());
            resultDto.setDiscipline(result.getDiscipline().name());
            resultDto.setValue(result.getValue());
            resultDto.setScore(result.getScore());
            resultDto.setCompetitorId(result.getCompetitor().getId());
            resultDtos.add(resultDto);
        }
        return resultDtos;
    }
}
