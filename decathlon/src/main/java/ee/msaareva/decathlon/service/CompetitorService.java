package ee.msaareva.decathlon.service;

import ee.msaareva.decathlon.entity.Competitor;
import ee.msaareva.decathlon.repository.CompetitorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CompetitorService {


    private final CompetitorRepository competitorRepository;

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
}
