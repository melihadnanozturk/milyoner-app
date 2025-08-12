package org.maoco.milyoner.question.service.impl;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.exception.AnswerException;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.question.data.entity.AnswerEntity;
import org.maoco.milyoner.question.data.entity.QuestionEntity;
import org.maoco.milyoner.question.data.repository.AnswerRepository;
import org.maoco.milyoner.question.data.repository.QuestionRepository;
import org.maoco.milyoner.question.domain.Answer;
import org.maoco.milyoner.question.domain.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionQueryService {

    private static final int WRONG_ANSWER_LIMIT = 3;
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

    private Question findQuestionByLevel(Long level) {
        QuestionEntity entity = questionRepository.findByQuestionLevel(level)
                .orElseThrow(() -> new NotFoundException("Question not found with level: " + level));
        return Question.of(entity);
    }


    public Question getQuestionByLevel(Long level) {
        Question question = this.findQuestionByLevel(level);

        List<Answer> answers = question.getAnswers();

        List<Answer> activateAnswers = answers.stream().filter(Answer::getIsActivate).toList();

        /*todo : AI'a sor => Veriler db de yer alıyor, dışarıdan yanlış bir bilgi eklenmesi söz konus olmaza (@Valid).
        *  DBden gelen bilgileri yine de kontrol etmek gerekir mi ? Alttaki gibi kontrol sağlamak gerekir mi ?  */
        List<Answer> correctAnswer = activateAnswers.stream().filter(Answer::getIsCorrect)
                .findFirst()
                .map(List::of)
                //todo : new exception
                .orElseThrow(() -> new NotFoundException("There is no correct answer in question with level: " + level));

        List<Answer> wrongAnswers = activateAnswers.stream()
                .filter(answer -> !answer.getIsCorrect())
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                            if (list.size() < WRONG_ANSWER_LIMIT) {
                                new AnswerException("There is not enough wrong answer in question with questionId: " + question.getId());
                            }
                            Collections.shuffle(list);
                            return list.stream().limit(WRONG_ANSWER_LIMIT).collect(Collectors.toList());
                        }
                ));

        List finalAnswers = new ArrayList();
        finalAnswers.addAll(correctAnswer);
        finalAnswers.addAll(wrongAnswers);

        question.setAnswers(finalAnswers);

        return question;
    }

    public AnswerEntity handleAnswer(Long questionId, Long answerId) {

        return answerRepository.findByIdAndQuestionIdAndIsActivate(answerId, questionId, true)
                .orElseThrow(() -> new NotFoundException("Answer not found with questionId :" + questionId + " and answerId: " + answerId));
    }
}
