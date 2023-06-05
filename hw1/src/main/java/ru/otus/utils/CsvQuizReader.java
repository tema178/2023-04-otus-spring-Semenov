package ru.otus.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import ru.otus.domain.QuizBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvQuizReader implements QuizReader {

    private final String csvPath;

    public CsvQuizReader(String csvPath) {
        this.csvPath = csvPath;
    }

    public List<QuizBody> getAllQuestions() throws IOException {
        return parseCSV(getFileFromResourceAsStream());
    }

    private List<QuizBody> parseCSV(InputStream csvInputStream) throws IOException {
        try (Reader in = new InputStreamReader(csvInputStream, StandardCharsets.UTF_8)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader().setSkipHeaderRecord(true).build().parse(in);
            List<QuizBody> quizBodies = new ArrayList<>();
            for (CSVRecord questionRecord : records) {
                quizBodies.add(QuizBody.builder()
                        .question(questionRecord.get("question"))
                        .answers(List.of(questionRecord.get("answer").split(ARRAY_DELIMITER)))
                        .numberOfTrueAnswer(Integer.parseInt(questionRecord.get("numberOfTrueAnswer"))).build());
            }
            return quizBodies;
        }
    }


    private InputStream getFileFromResourceAsStream() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(csvPath);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + csvPath);
        } else {
            return inputStream;
        }
    }
}
