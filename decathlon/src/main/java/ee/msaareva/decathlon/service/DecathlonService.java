package ee.msaareva.decathlon.service;

import ee.msaareva.decathlon.entity.*;
import ee.msaareva.decathlon.repository.CompetitorRepository;
import ee.msaareva.decathlon.repository.ResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class DecathlonService {

    private final CompetitorRepository competitorRepository;
    private final ResultRepository resultRepository;
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Kohtunik> getKohtunikud() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://69fcd29d30ad0a6fd1c03052.mockapi.io/fakedata/Kohtunikud"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return Arrays.asList(
                mapper.readValue(response.body(), Kohtunik[].class)
        );
    }

    public List<Location> getLocations() throws IOException, InterruptedException {
        HttpRequest builder = HttpRequest.newBuilder()
                .uri(URI.create("https://69fcd29d30ad0a6fd1c03052.mockapi.io/fakedata/Location"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(builder, HttpResponse.BodyHandlers.ofString());
        return Arrays.asList(
                mapper.readValue(response.body(), Location[].class)
        );
    }

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

