package org.maoco.milyoner.question.service;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.question.data.entity.AnswerEntity;
import org.maoco.milyoner.question.data.entity.QuestionEntity;
import org.maoco.milyoner.question.data.repository.AnswerRepository;
import org.maoco.milyoner.question.data.repository.QuestionRepository;
import org.maoco.milyoner.question.domain.Question;
import org.maoco.milyoner.question.service.spec.QuestionSpecification;
import org.maoco.milyoner.question.web.dto.request.QuestionQueryRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionQueryService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public Collection<Question> getAllQuestions(QuestionQueryRequest request) {

        Specification<QuestionEntity> spec = QuestionSpecification.getQuestionsByQuestionLevel(request.getQuestionLevel());
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());

        List<QuestionEntity> content = questionRepository.findAll(spec, pageable).getContent();
        return content.stream().map(Question::of).toList();

    }

    public Question getQuestionById(Long id) {
        QuestionEntity entity = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found with id: " + id));

        return Question.of(entity);
    }

    //for gamePlay
    public Question getQuestionByLevel(Long level) {
        QuestionEntity entity = questionRepository.findByQuestionLevel(level, true)
                .orElseThrow(() -> new NotFoundException("Question not found with level: " + level));

        return Question.of(entity);
    }

    public AnswerEntity handleAnswer(Long questionId, Long answerId) {
        return answerRepository.findByIdAndQuestionIdAndIsActivate(answerId, questionId, true)
                .orElseThrow(() -> new NotFoundException("Answer not found with questionId :" + questionId + " and answerId: " + answerId));
    }
}
