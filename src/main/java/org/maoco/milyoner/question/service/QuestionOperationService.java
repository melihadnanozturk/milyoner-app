package org.maoco.milyoner.question.service;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.exception.AnswerException;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.question.data.entity.AnswerEntity;
import org.maoco.milyoner.question.data.entity.QuestionEntity;
import org.maoco.milyoner.question.data.repository.QuestionRepository;
import org.maoco.milyoner.question.domain.Question;
import org.maoco.milyoner.question.service.exception.CreateAnswerException;
import org.maoco.milyoner.question.web.dto.request.CreateNewQuestionRequest;
import org.maoco.milyoner.question.web.dto.request.CreateQuestionAnswer;
import org.maoco.milyoner.question.web.dto.request.UpdateQuestionRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionOperationService {

    private final QuestionRepository questionRepository;

    public Question createNewQuestion(CreateNewQuestionRequest request) {

        if (request.getActivate()) {
            this.checkTrueAnswerNumber(request.getAnswers());
        }

        QuestionEntity entity = new QuestionEntity();
        entity.setQuestionText(request.getQuestionText());
        entity.setQuestionLevel(request.getQuestionLevel());

        List<AnswerEntity> answerEntities = request.getAnswers().stream()
                .map(answer ->
                        new AnswerEntity(answer.getAnswerText(), answer.getIsCorrect(), entity)
                )
                .toList();

        entity.setAnswers(answerEntities);
        entity.setIsActivate(request.getActivate());

        QuestionEntity saved = questionRepository.save(entity);

        return Question.of(saved);
    }

    public Question updateQuestion(UpdateQuestionRequest request) {
        QuestionEntity entity = questionRepository.findById(request.getQuestionId()).orElseThrow(() -> new NotFoundException("Question not found"));

        this.checkQuestion(request, entity);

        entity.setQuestionText(request.getQuestionText());
        entity.setQuestionLevel(request.getQuestionLevel());
        entity.setIsActivate(request.getIsActivate());

        QuestionEntity saved = questionRepository.save(entity);
        return Question.of(saved);
    }

    public void setDeactivate(Long id) {
        QuestionEntity entity = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found with id: " + id));

        entity.setIsActivate(false);
        questionRepository.save(entity);
    }

    public String deleteQuestion(Long questionId) {
        questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("Question not found"));

        questionRepository.deleteById(questionId);
        return String.format("Question with id %d deleted", questionId);
    }

    private void checkTrueAnswerNumber(List<CreateQuestionAnswer> answers) {
        List<CreateQuestionAnswer> trueAnswers = answers.stream().filter(CreateQuestionAnswer::getIsCorrect).toList();
        if (trueAnswers.size() != 1) throw new CreateAnswerException();
    }

    private void checkQuestion(UpdateQuestionRequest request, QuestionEntity entity) {
        if (request.getIsActivate() && entity.getIsActivate().equals(false)) {
            List<AnswerEntity> activateAnswers = entity.getAnswers().stream().filter(AnswerEntity::getIsActivate).toList();

            int activeAnswerNumber = activateAnswers.size();
            int correctAnswerNumber = activateAnswers.stream().filter(AnswerEntity::getIsCorrect).toList().size();

            if (correctAnswerNumber != 1) {
                throw new AnswerException("Soruyu aktifleştirirken sorun oldu. Her sorunun sadece bir tane doğru cevabı olmak zorundadır. Lütfen cevapları kontrol edin");
            }

            if (activeAnswerNumber < 4) {
                throw new AnswerException("Her sorunun minimum 4 tane cevabı olmak zorundadır. Lütfen cevap sayıları kontrol edin");
            }
        }

    }
}
