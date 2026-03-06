package ee.msaareva.kontrolltoo.controller;

import ee.msaareva.kontrolltoo.entity.Word;
import ee.msaareva.kontrolltoo.repository.WordRepository;
import ee.msaareva.kontrolltoo.service.WordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor

public class LetterController {
    private final WordRepository wordRepository;
    private final WordService wordService;

    @GetMapping("words")
    public List<Word> getLetters() {
        return wordRepository.findAll();
    }

    @PostMapping("word")
    public Word addWord(@RequestBody Word word) {
        wordService.validateWord(word);
        return wordRepository.save(word);
    }
}
