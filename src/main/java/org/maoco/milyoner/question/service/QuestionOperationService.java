package org.maoco.milyoner.question.service;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.question.domain.Question;
import org.maoco.milyoner.question.web.dto.request.CreateAnswerQuestion;
import org.maoco.milyoner.question.web.dto.request.CreateNewQuestionRequest;
import org.maoco.milyoner.question.web.dto.request.UpdateQuestionRequest;
import org.maoco.milyoner.question.data.entity.AnswerEntity;
import org.maoco.milyoner.question.data.entity.QuestionEntity;
import org.maoco.milyoner.question.data.repository.QuestionRepository;
import org.maoco.milyoner.question.service.exception.CreateAnswerException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionOperationService {

    private final QuestionRepository questionRepository;

    public Question createNewQuestion(CreateNewQuestionRequest request) {
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

        QuestionEntity saved = questionRepository.save(entity);

        return Question.of(saved);
    }

    public Question updateQuestion(UpdateQuestionRequest request) {
        QuestionEntity entity = questionRepository.findById(request.getQuestionId()).orElseThrow(() -> new NotFoundException("Question not found"));

        entity.setQuestionText(request.getQuestionText());
        entity.setQuestionLevel(request.getQuestionLevel());

        QuestionEntity saved = questionRepository.save(entity);
        return Question.of(saved);
    }

    public void setDeactivate(Long id){
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

    private void checkTrueAnswerNumber(List<CreateAnswerQuestion> answers) {
        List<CreateAnswerQuestion> trueAnswers = answers.stream().filter(q -> q.getIsCorrect()).toList();
        if (trueAnswers.size() != 1) throw new CreateAnswerException();
    }
}
