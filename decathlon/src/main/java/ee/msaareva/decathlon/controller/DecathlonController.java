package ee.msaareva.decathlon.controller;


import ee.msaareva.decathlon.entity.Competitor;
import ee.msaareva.decathlon.entity.Result;
import ee.msaareva.decathlon.repository.CompetitorRepository;
import ee.msaareva.decathlon.repository.ResultRepository;
import ee.msaareva.decathlon.service.CompetitorService;
import ee.msaareva.decathlon.service.DecathlonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class DecathlonController {
    private final CompetitorRepository competitorRepository;
    private final CompetitorService competitorService;
    private final ResultRepository resultRepository;
    private final DecathlonService decathlonService;

    @GetMapping("athletes")
    public List<Competitor> getAthlete() {
        return competitorRepository.findAll();
    }

    @PostMapping("athletes")
    public Competitor postAthlete(@RequestBody Competitor competitor) {
        competitorService.validateCompetitor(competitor);
        return competitorRepository.save(competitor);
    }

    @GetMapping("athletes/results")
    public List<Result> getResults() {
        return resultRepository.findAll();
    }

    @PostMapping("athletes/{id}/results")
    public Result addResult(@PathVariable Long id, @RequestBody Result result) {
        return decathlonService.addResultToCompetitor(id, result);
    }

    @GetMapping("athletes/{id}/results")
    public List<Result> getAthleteResults(@PathVariable Long id) {
        return resultRepository.findAllByCompetitorId(id);
    }
}
