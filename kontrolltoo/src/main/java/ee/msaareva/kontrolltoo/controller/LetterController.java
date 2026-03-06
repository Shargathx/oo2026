package ee.msaareva.kontrolltoo.controller;

import ee.msaareva.kontrolltoo.entity.Word;
import ee.msaareva.kontrolltoo.repository.WordRepository;
import ee.msaareva.kontrolltoo.service.WordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor

public class LetterController {
    private final WordRepository wordRepository;
    private final WordService wordService;

    @GetMapping("words")
    public List<Word> getWords() {
        return wordRepository.findAll();
    }

    @PostMapping("word")
    public Word addWord(@RequestBody Word word) {
        wordService.validateWord(word);
        return wordRepository.save(word);
    }

    @GetMapping("letters")
    public int getLetters() {
        return wordService.countAndSaveTotalAs();
    }

    @GetMapping("words-containing-a")
    public ArrayList<Word> findWordsContainingA() {
        return wordService.findWordsContainingLetterA();
    }

    @GetMapping("total-a-count")
    public double getTotalACount() {
        return wordService.getAverageACount();
    }

    @GetMapping("assign-letter")
    public ArrayList<String> assignLetter(@RequestParam int number) {
        return wordService.assignLetter(number);
    }







}
