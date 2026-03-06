package ee.msaareva.kontrolltoo.service;

import ee.msaareva.kontrolltoo.entity.Word;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WordService {
    public void validateWord(Word word) {
        if (word.getWord().isEmpty()) {
            throw new RuntimeException("Word cannot be empty");
        }
    }
}
