package net.java.lms_backend.Service;

import net.java.lms_backend.Repositrory.CourseRepository;
import net.java.lms_backend.Repositrory.QuizAttemptRepository;
import net.java.lms_backend.Repositrory.QuizRepository;
import net.java.lms_backend.Repositrory.UserRepository;
import net.java.lms_backend.dto.QuizDTO;
import net.java.lms_backend.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizAttemptRepository quizAttemptRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private User mockUser;
    @InjectMocks
    private QuizService quizService;

    private Quiz quiz;
    private Course course;
    private User student;
    private QuizDTO quizDTO;
    private QuizAttempt quizAttempt;
    private List<Question> questions;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);

        quiz = new Quiz();
        quiz.setId(1L);
        quiz.setNumOfMCQ(2L);
        quiz.setNumOfTrueFalse(2L);
        quiz.setNumOfShortAnswer(1L);
        quiz.setCourse(course);

        student = new User();
        student.setId(1L);

        quizDTO = new QuizDTO();
        quizDTO.setNumOfMCQ(2L);
        quizDTO.setNumOfTrueFalse(2L);
        quizDTO.setNumOfShortAnswer(1L);

        questions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Question question = new Question();
            question.setId((long) i);
            if (i < 2) {
                question.setType(QuestionType.MCQ);
            }
            else if (i < 4) {
                question.setType(QuestionType.TRUE_FALSE);

            }
            else {
                question.setType(QuestionType.SHORT_ANSWER);
            }
            question.setContent("Question " + i);
            question.setCorrectAnswer("correct" + i);
            questions.add(question);
        }
        course.setQuestionsBank(questions);

        quizAttempt = new QuizAttempt();
        quizAttempt.setId(1L);
        quizAttempt.setQuiz(quiz);
        quizAttempt.setStudent(student);
        quizAttempt.setQuestions(questions);
    }


    @Test
    void createQuiz_CourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> quizService.createQuiz(1L, quizDTO));
        verify(quizRepository, never()).save(any(Quiz.class));
    }


    @Test
    void getAverageScoreByQuizId_Success() {
        List<QuizAttempt> attempts = Arrays.asList(
                createQuizAttemptWithScore(3),
                createQuizAttemptWithScore(4)
        );

        when(quizAttemptRepository.findByQuizId(1L)).thenReturn(attempts);

        double average = quizService.getAverageScoreByQuizId(1L);

        assertEquals(3.5, average);
    }

    @Test
    void getQuizAttemptsByStudent_Success() {
        List<QuizAttempt> attempts = Arrays.asList(quizAttempt);
        when(quizAttemptRepository.findByStudentIdAndQuiz_Course_Id(1L, 1L))
                .thenReturn(attempts);

        List<QuizAttempt> result = quizService.getQuizAttemptsByStudent(1L, 1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getAverageScoreOfStudent_Success() {
        List<QuizAttempt> attempts = Arrays.asList(
                createQuizAttemptWithScore(3),
                createQuizAttemptWithScore(5)
        );

        when(quizAttemptRepository.findByStudentIdAndQuiz_Course_Id(1L, 1L))
                .thenReturn(attempts);

        Double average = quizService.getAverageScoreOfStudent(1L, 1L);

        assertEquals(4.0, average);
    }

    private QuizAttempt createQuizAttemptWithScore(int score) {
        QuizAttempt attempt = new QuizAttempt();
        attempt.setScore(score);
        return attempt;
    }
}