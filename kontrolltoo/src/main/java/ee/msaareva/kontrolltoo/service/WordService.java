package ee.msaareva.kontrolltoo.service;

import ee.msaareva.kontrolltoo.entity.LetterCounter;
import ee.msaareva.kontrolltoo.entity.Word;
import ee.msaareva.kontrolltoo.repository.LetterCounterRepository;
import ee.msaareva.kontrolltoo.repository.WordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WordService {
    private final WordRepository wordRepository;
    private final LetterCounterRepository letterCounterRepository;

    public void validateWord(Word word) {
        if (word.getWord().isEmpty() || word.getWord() == null) {
            throw new RuntimeException("Word cannot be empty or null");
        }
        if (word.getWord().isBlank() || word.getWord() == null) {
            throw new RuntimeException("Word cannot be blank or null");
        }
        if (word.getWord().length() >= 46) {
            throw new RuntimeException("Word is too long, cannot exceed 45 characters");
        }
        // selle alumise idee genereeris GPT:
        if (!word.getWord().matches("[a-zA-Z]+")) {
            throw new RuntimeException("Word must contain only letters");
        }
        if (wordRepository.existsByWord((word.getWord()))) {
            throw new RuntimeException("Word already exists");
        }
    }

    public int countAndSaveTotalAs() {
        List<Word> allWords = wordRepository.findAll();
        int totalAs = 0;

        for (Word wordWithLetterA : allWords) {
            String lowerCaseWord = wordWithLetterA.getWord().toLowerCase();
            totalAs += lowerCaseWord.length() - lowerCaseWord.replace("a", "").length();
        }

        LetterCounter letterCounter = new LetterCounter();
        letterCounter.setTotalACount(totalAs);
        letterCounterRepository.save(letterCounter);

        return totalAs;
    }

    public ArrayList<Word> findWordsContainingLetterA() {
        List<Word> allWords = wordRepository.findAll();
        ArrayList<Word> wordsWithLetterA = new ArrayList<>();
        for (Word wordWithLetterA : allWords) {
            if (wordWithLetterA.getWord().toLowerCase().contains("a")) {
                wordsWithLetterA.add(wordWithLetterA);
            }
        }
        return wordsWithLetterA;
    }

    public double getAverageACount() {
        List<Word> allWords = wordRepository.findAll();
        int totalLetters = 0;
        int totalAs = 0;

        for (Word word : allWords) {
            totalLetters += word.getWord().length();
            totalAs += word.getWord().toLowerCase().length() - word.getWord().toLowerCase().replace("a", "").length();
        }
        if (totalLetters == 0) {
            return 0;
        }
        return (double) totalAs / totalLetters;
    }

    public ArrayList<String> assignLetter(int position) {
        List<Word> allWords = wordRepository.findAll();
        ArrayList<String> updatedWords = new ArrayList<>();
        boolean wordLongEnough = false;

        for (Word word : allWords) {
            String lowerCaseWord = word.getWord().toLowerCase();
            if (lowerCaseWord.contains("a")) {
                if (position > 0 && position <= lowerCaseWord.length()) {
                    String newWord = lowerCaseWord.substring(0, position - 1) + "a" + lowerCaseWord.substring(position);
                    updatedWords.add(newWord);
                    wordLongEnough = true;
                }
                else {
                    updatedWords.add(lowerCaseWord);
                }
            }
        }
        if (position <= 0) {
            throw new RuntimeException("Position cannot be less than zero");
        }
        if (!wordLongEnough) {
            throw new RuntimeException("Position is too long");
        }
        return updatedWords;
    }
}
