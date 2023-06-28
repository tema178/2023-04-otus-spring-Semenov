package ru.otus.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import ru.otus.config.PathProvider;
import ru.otus.domain.Answer;
import ru.otus.domain.Quiz;
import ru.otus.exceptions.QuizReaderException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvQuizReader implements QuizReader {

    private static final String ARRAY_DELIMITER = ";";

    private static final String QUESTION_FIELD_NAME = "question";

    private static final String ANSWERS_FIELD_NAME = "answers";

    private static final String NUMBER_OF_CORRECT_ANSWER_FIELD_NAME = "numberOfCorrectAnswer";

    private final PathProvider pathProvider;

    public CsvQuizReader(PathProvider pathProvider) {
        this.pathProvider = pathProvider;
    }

    public List<Quiz> getAllQuestions() throws QuizReaderException {
        try {
            return parseCSV(getFileFromResourceAsStream());
        } catch (IOException e) {
            throw new QuizReaderException(e);
        }
    }

    private List<Quiz> parseCSV(InputStream csvInputStream) throws IOException {
        try (Reader in = new InputStreamReader(csvInputStream, StandardCharsets.UTF_8)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader().setSkipHeaderRecord(true).build().parse(in);

            List<Quiz> quizList = new ArrayList<>();
            for (CSVRecord quizRecord : records) {
                String question = quizRecord.get(QUESTION_FIELD_NAME);
                List<String> answerValues = List.of(quizRecord.get(ANSWERS_FIELD_NAME).split(ARRAY_DELIMITER));
                int numberOfCorrectAnswer = Integer.parseInt(quizRecord.get(NUMBER_OF_CORRECT_ANSWER_FIELD_NAME));
                validateNumberOfCorrectAnswer(question, answerValues, numberOfCorrectAnswer);

                List<Answer> answers = new ArrayList<>();
                for (int i = 0; i < answerValues.size(); i++) {
                    answers.add(new Answer(answerValues.get(i), i + 1 == numberOfCorrectAnswer));
                }

                quizList.add(new Quiz(question, answers));
            }
            return quizList;
        }
    }

    private static void validateNumberOfCorrectAnswer(String question,
                                                      List<String> answerValues,
                                                      int numberOfTrueAnswer) throws IOException {
        if (numberOfTrueAnswer < 1 || numberOfTrueAnswer > answerValues.size()) {
            throw new IOException(String.format(
                    "Can't define correct answer. Value of %s for question '%s' should be in [1, %s]",
                    NUMBER_OF_CORRECT_ANSWER_FIELD_NAME, question, answerValues.size()));
        }
    }

    private InputStream getFileFromResourceAsStream() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(pathProvider.getPath());
        if (inputStream == null) {
            throw new IOException(pathProvider + " file not found.");
        } else {
            return inputStream;
        }
    }
}
