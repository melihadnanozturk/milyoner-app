package org.maoco.milyoner.question.service;

import org.maoco.milyoner.common.error.CommonError;
import org.maoco.milyoner.common.error.PanelError;
import org.maoco.milyoner.common.exception.AnswerException;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.question.data.entity.AnswerEntity;
import org.maoco.milyoner.question.data.entity.QuestionEntity;
import org.maoco.milyoner.question.data.repository.AnswerRepository;
import org.maoco.milyoner.question.domain.Answer;
import org.maoco.milyoner.question.domain.Question;
import org.maoco.milyoner.question.web.dto.request.CreateNewAnswerRequest;
import org.maoco.milyoner.question.web.dto.request.UpdateAnswerRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerOperationService {

    private final AnswerRepository repository;
    private final QuestionQueryService questionqueryService;
    private final QuestionOperationService questionOperationService;

    public AnswerOperationService(AnswerRepository repository, QuestionQueryService questionqueryService, QuestionOperationService questionOperationService) {
        this.repository = repository;
        this.questionqueryService = questionqueryService;
        this.questionOperationService = questionOperationService;
    }


    public Answer createNewAnswer(CreateNewAnswerRequest request) {
        Question question = questionqueryService.getQuestionById(request.getQuestionId());
        this.checkAnswerForNewCreate(request, question);

        AnswerEntity entity = AnswerEntity.builder()
                .answerText(request.getAnswerText())
                .isActivate(request.getIsActive())
                .isCorrect(request.getIsCorrect())
                .question(QuestionEntity.of(question))
                .build();

        AnswerEntity saved = repository.save(entity);
        return Answer.of(saved);
    }


    public Answer updateAnswer(UpdateAnswerRequest request) {
        AnswerEntity entity = repository.findById(request.getAnswerId())
                .orElseThrow(() -> new NotFoundException(CommonError.NOT_FOUND.getMessage()));

        this.checkAnswerForUpdate(entity, request);

        entity.setAnswerText(request.getAnswerText());
        entity.setIsCorrect(request.getIsCorrect());
        entity.setIsActivate(request.getIsActive());

        AnswerEntity saved = repository.save(entity);
        return Answer.of(saved);
    }

    public String deleteAnswer(Long answerId) {
        AnswerEntity answerEntity = repository.findById(answerId)
                .orElseThrow(() -> new NotFoundException(CommonError.NOT_FOUND.getMessage()));

        QuestionEntity questionEntity = answerEntity.getQuestion();
        int activateAnswer = questionEntity.getAnswers().stream().filter(AnswerEntity::getIsActivate).toList().size();

        if (answerEntity.getIsActivate()) {
            if (answerEntity.getIsCorrect() || activateAnswer == 4) {
                questionOperationService.setDeactivate(questionEntity.getId());
                questionEntity.removeAnswer(answerEntity);
                repository.deleteById(answerId);
                return "Question has been deactivated";
            }
        }

        questionEntity.removeAnswer(answerEntity);
        repository.deleteById(answerId);
        return "Record was deleted successfully";
    }

    private void checkAnswerForUpdate(AnswerEntity entity, UpdateAnswerRequest request) {
        QuestionEntity question = entity.getQuestion();
        List<AnswerEntity> allAnswers = question.getAnswers();

        boolean newIsActive = request.getIsActive();
        boolean newIsCorrect = request.getIsCorrect();

        List<AnswerEntity> otherAnswers = allAnswers.stream()
                .filter(answer -> !answer.getId().equals(entity.getId()))
                .toList();

        long otherActiveAnswers = otherAnswers.stream()
                .filter(AnswerEntity::getIsActivate)
                .count();

        long otherActiveCorrectAnswers = otherAnswers.stream()
                .filter(answer -> answer.getIsActivate() && answer.getIsCorrect())
                .count();

        long otherActiveWrongAnswers = otherAnswers.stream()
                .filter(answer -> answer.getIsActivate() && !answer.getIsCorrect())
                .count();

        long activeAnswersAfterUpdate = otherActiveAnswers + (newIsActive ? 1 : 0);
        long correctAnswersAfterUpdate = otherActiveCorrectAnswers + (newIsActive && newIsCorrect ? 1 : 0);
        long wrongAnswersAfterUpdate = otherActiveWrongAnswers + (newIsActive && !newIsCorrect ? 1 : 0);

        if (activeAnswersAfterUpdate < 4) {
            throw new AnswerException(PanelError.ACTIVE_ANSWER_NUMBER_ERROR.getMessage());
        }

        if (correctAnswersAfterUpdate != 1) {
            throw new AnswerException(PanelError.CORRECT_ANSWER_NUMBER_ERROR.getMessage());
        }

        if (wrongAnswersAfterUpdate < 3) {
            throw new AnswerException(PanelError.WRONG_ANSWER_NUMBER_ERROR.getMessage());
        }
    }

    private void checkAnswerForNewCreate(CreateNewAnswerRequest request, Question question) {
        if (!request.getIsActive()) {
            return;
        }

        if (request.getIsCorrect()) {
            List<Answer> answers = question.getAnswers().stream().filter(Answer::getIsCorrect).toList();

            if (!answers.isEmpty()) {
                throw new AnswerException(PanelError.CORRECT_ANSWER_NUMBER_ERROR.getMessage());
            }
        }
    }
}
