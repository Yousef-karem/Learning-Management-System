package net.java.lms_backend.Service;
import net.java.lms_backend.Repositrory.*;
import net.java.lms_backend.dto.QuizDTO;
import net.java.lms_backend.entity.*;
import net.java.lms_backend.mapper.QuizMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final CourseRepository courseRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public QuizService(QuizRepository quizRepository,
                       QuizAttemptRepository quizAttemptRepository,
                       CourseRepository courseRepository,
                       NotificationRepository notificationRepository, NotificationService notificationService, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.courseRepository = courseRepository;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    public Quiz createQuiz(Long courseId, QuizDTO quizDTO) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        Quiz quiz = QuizMapper.toEntity(quizDTO);
        quiz.setCourse(course);
        notificationService.notifyAll(courseId,"There is a new quiz");
        return quizRepository.save(quiz);
    }

    public QuizAttempt generateQuizAttempt(Long quizId , Long studentId) {

        Quiz quiz = quizRepository.findById( quizId ).orElseThrow(() -> new RuntimeException("Quiz not found"));

        User student = userRepository.findById( studentId ).orElseThrow(() -> new RuntimeException("Student not found"));

        List<Question> questions = quiz.getCourse().getQuestionsBank();

        List<Question> mcqQuestions = getRandomQuestions(questions, "MCQ", quiz.getNumOfMCQ());
        List<Question> trueFalseQuestions = getRandomQuestions(questions, "TRUE_FALSE", quiz.getNumOfTrueFalse());
        List<Question> shortAnswerQuestions = getRandomQuestions(questions, "SHORT_ANSWER", quiz.getNumOfShortAnswer());

        List<Question> selectedQuestions = new ArrayList<>();
        selectedQuestions.addAll(mcqQuestions);
        selectedQuestions.addAll(trueFalseQuestions);
        selectedQuestions.addAll(shortAnswerQuestions);

        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setQuiz(quiz);
        quizAttempt.setStudent(student);
        quizAttempt.setQuestions(selectedQuestions);

        return quizAttemptRepository.save(quizAttempt);
    }

    private List<Question> getRandomQuestions(List<Question> questions, String type, Long count) {
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.getType().equalsIgnoreCase(type)) {
                filteredQuestions.add(question);
            }
        }
        Collections.shuffle(filteredQuestions);
        return filteredQuestions.subList(0, Math.toIntExact(count));
    }

    public int updateQuizAttempt(Long quizAttemptId, Map<Long, String> answers) {
        QuizAttempt quizAttempt = quizAttemptRepository.findById(quizAttemptId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz attempt not found"));

        quizAttempt.setAnswers(answers);

        int score = 0;
        for (var entry : answers.entrySet()) {
            Long questionId = entry.getKey();
            String studentAnswer = entry.getValue();

            var question = quizAttempt.getQuestions().stream()
                    .filter(q -> q.getId().equals(questionId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid question ID: " + questionId));

            if (question.getCorrectAnswer().equals(studentAnswer)) {
                score++;
            }
        }

        quizAttempt.setScore(score);
        quizAttemptRepository.save(quizAttempt);
        notificationService.notify( quizAttempt.getStudent(),
                "You got score "+score+" at Quiz"
                );
        return score;
    }
    public QuizAttempt getQuizAttempt(Long quizAttemptId) {
        QuizAttempt quizAttempt = quizAttemptRepository.findById(quizAttemptId).orElseThrow(() -> new RuntimeException("Quiz attempt not found"));
        return quizAttempt;
    }

    public double getAverageScoreByQuizId(Long quizId) {
        List<QuizAttempt> quizAttempts = quizAttemptRepository.findByQuizId(quizId);
        if (quizAttempts.isEmpty()) {
            return 0.0;
        }
        int totalScore = 0;
        for (QuizAttempt quizAttempt : quizAttempts) {
            totalScore += quizAttempt.getScore();
        }
        return (double) totalScore / quizAttempts.size();
    }

    public List<QuizAttempt> getQuizAttemptsByStudent(Long studentId, long courseId) {
        List<QuizAttempt> quizAttempts = quizAttemptRepository.findByStudentIdAndQuiz_Course_Id(studentId, courseId); // or adjust based on your logic
        return quizAttempts;
    }

    public Double getAverageScoreOfStudent(Long studentId, long courseId) {
        List<QuizAttempt> quizAttempts = quizAttemptRepository.findByStudentIdAndQuiz_Course_Id(studentId, courseId); // or adjust based on your logic
        OptionalDouble average = quizAttempts.stream()
                .mapToDouble(QuizAttempt::getScore)  // Assuming getScore() is a method in QuizAttempt entity
                .average();

        return average.isPresent() ? average.getAsDouble() : 0.0;
    }
}
