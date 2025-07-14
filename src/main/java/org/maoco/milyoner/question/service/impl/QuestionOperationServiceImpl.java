package org.maoco.milyoner.question.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.maoco.milyoner.question.controller.request.CreateNewQuestionRequest;
import org.maoco.milyoner.question.controller.request.UpdateQuestionRequest;
import org.maoco.milyoner.question.entity.AnswerEntity;
import org.maoco.milyoner.question.entity.QuestionEntity;
import org.maoco.milyoner.question.repository.QuestionRepository;
import org.maoco.milyoner.question.service.QuestionOperationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionOperationServiceImpl implements QuestionOperationService {

    private final QuestionRepository questionRepository;

    @Override
    public QuestionEntity createNewQuestion(CreateNewQuestionRequest request) {
        QuestionEntity entity = new QuestionEntity();
        entity.setQuestionText(request.getQuestionText());

        List<AnswerEntity> answerEntities = request.getAnswers().stream()
                .map(answer -> new AnswerEntity(answer.getAnswerText(), answer.getIsCorrect()))
                .toList();

        entity.setAnswers(answerEntities);

        return questionRepository.save(entity);
    }

    @Override
    public QuestionEntity updateQuestion(UpdateQuestionRequest request) {
        QuestionEntity entity = questionRepository.findById(request.getQuestionId()).orElseThrow(() -> new ServiceException("Question not found"));

        entity.setQuestionText(request.getQuestionText());

        return questionRepository.save(entity);
    }

    @Override
    public String deleteQuestion(Long questionId) {
        questionRepository.findById(questionId).orElseThrow(() -> new ServiceException("Question not found"));

        questionRepository.deleteById(questionId);
        return String.format("Question with id %d deleted", questionId);
    }
}
