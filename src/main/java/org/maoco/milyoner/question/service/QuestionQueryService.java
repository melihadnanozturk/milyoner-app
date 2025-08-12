package org.maoco.milyoner.question.service;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.question.data.entity.AnswerEntity;
import org.maoco.milyoner.question.data.entity.QuestionEntity;
import org.maoco.milyoner.question.data.repository.AnswerRepository;
import org.maoco.milyoner.question.data.repository.QuestionRepository;
import org.maoco.milyoner.question.domain.Question;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionQueryService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public Collection<QuestionEntity> getAllQuestions() {
        /*return questionRepository.findAll();*/

        return List.of();
    }

    public Question getQuestionById(Long id) {
        QuestionEntity entity = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found with id: " + id));

        return Question.of(entity);
    }

    public Question getQuestionByLevel(Long level) {
        return this.findQuestionByLevel(level);
    }

    public AnswerEntity handleAnswer(Long questionId, Long answerId) {

        return answerRepository.findByIdAndQuestionIdAndIsActivate(answerId, questionId, true)
                .orElseThrow(() -> new NotFoundException("Answer not found with questionId :" + questionId + " and answerId: " + answerId));
    }

    private Question findQuestionByLevel(Long level) {
        QuestionEntity entity = questionRepository.findByQuestionLevel(level)
                .orElseThrow(() -> new NotFoundException("Question not found with level: " + level));
        return Question.of(entity);
    }
}
