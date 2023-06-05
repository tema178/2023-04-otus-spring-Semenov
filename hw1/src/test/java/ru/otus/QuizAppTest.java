package ru.otus;

import junit.framework.TestCase;
import ru.otus.dao.QuizDao;
import ru.otus.domain.QuizBody;
import ru.otus.service.QuizService;
import ru.otus.utils.QuizPrinter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class QuizAppTest extends TestCase {

    /**
     * Test output format
     */
    public void testApp() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        new QuizService(new MockQuizDao(), new QuizPrinter()).printQuiz();

        assertEquals(baos.toString(), "Anemophobia is the fear of what?\n" +
                "1. The Dark\n" +
                "2. Fire\n" +
                "3. Wind\n");
    }

    private static class MockQuizDao extends QuizDao {

        public MockQuizDao() {
            super(null);
        }

        @Override
        public List<QuizBody> getQuestions() {
            return List.of(new QuizBody("Anemophobia is the fear of what?",
                    List.of("The Dark", "Fire", "Wind"),
                    3));
        }
    }
}
