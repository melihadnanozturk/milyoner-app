package org.maoco.milyoner.question.service.impl;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.question.controller.request.CreateAnswerQuestion;
import org.maoco.milyoner.question.controller.request.CreateNewQuestionRequest;
import org.maoco.milyoner.question.controller.request.UpdateQuestionRequest;
import org.maoco.milyoner.question.entity.AnswerEntity;
import org.maoco.milyoner.question.entity.QuestionEntity;
import org.maoco.milyoner.question.repository.QuestionRepository;
import org.maoco.milyoner.question.service.QuestionOperationService;
import org.maoco.milyoner.question.service.exception.CreateAnswerException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionOperationServiceImpl implements QuestionOperationService {

    private final QuestionRepository questionRepository;

    private void checkTrueAnswerNumber(List<CreateAnswerQuestion> answers) {
        List<CreateAnswerQuestion> trueAnswers = answers.stream().filter(q -> q.getIsCorrect()).toList();
        if (trueAnswers.size() != 1) throw new CreateAnswerException();
    }

    @Override
    public QuestionEntity createNewQuestion(CreateNewQuestionRequest request) {
        this.checkTrueAnswerNumber(request.getAnswers());

        QuestionEntity entity = new QuestionEntity();
        entity.setQuestionText(request.getQuestionText());
        entity.setQuestionLevel(request.getQuestionLevel());

        List<AnswerEntity> answerEntities = request.getAnswers().stream()
                .map(answer ->
                     new AnswerEntity(answer.getAnswerText(), answer.getIsCorrect(), entity)
                )
                .toList();

        entity.setAnswers(answerEntities);

        return questionRepository.save(entity);
    }

    @Override
    public QuestionEntity updateQuestion(UpdateQuestionRequest request) {
        QuestionEntity entity = questionRepository.findById(request.getQuestionId()).orElseThrow(() -> new NotFoundException("Question not found"));

        entity.setQuestionText(request.getQuestionText());
        entity.setQuestionLevel(request.getQuestionLevel());

        return questionRepository.save(entity);
    }

    @Override
    public String deleteQuestion(Long questionId) {
        questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("Question not found"));

        questionRepository.deleteById(questionId);
        return String.format("Question with id %d deleted", questionId);
    }
}
