package net.java.lms_backend.mapper;

import net.java.lms_backend.dto.QuestionDTO;
import net.java.lms_backend.entity.Course;
import net.java.lms_backend.entity.Question;

public class QuestionMapper {

    public static QuestionDTO mapToQuestionDTO(Question question) {
        return new QuestionDTO(
                question.getId(),
                question.getContent(),
                question.getCorrectAnswer(),
                question.getOptions(),
                question.getCourse() != null ? question.getCourse().getId() : null,
                question.getType()
        );
    }

    public static Question mapToQuestion(QuestionDTO questionDTO, Course course) {
        return new Question(
                questionDTO.getContent(),
                questionDTO.getType(),
                questionDTO.getOptions(),
                questionDTO.getCorrectAnswer(),
                course
        );
    }
}
