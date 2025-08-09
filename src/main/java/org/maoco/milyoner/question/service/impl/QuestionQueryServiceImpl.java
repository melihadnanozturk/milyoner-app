package org.maoco.milyoner.question.service.impl;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.exception.AnswerException;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.question.entity.AnswerEntity;
import org.maoco.milyoner.question.entity.QuestionEntity;
import org.maoco.milyoner.question.repository.AnswerRepository;
import org.maoco.milyoner.question.repository.QuestionRepository;
import org.maoco.milyoner.question.service.QuestionQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionQueryServiceImpl implements QuestionQueryService {

    private static final int WRONG_ANSWER_LIMIT = 3;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    public Collection<QuestionEntity> getAllQuestions() {
        /*return questionRepository.findAll();*/

        return List.of();
    }

    @Override
    public QuestionEntity getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NotFoundException("Question not found with id: " + id));
    }

    private QuestionEntity findByQuestionByLevel(Long level) {
        return questionRepository.findByQuestionLevel(level)
                .orElseThrow(() -> new NotFoundException("Question not found with level: " + level));
    }


    @Override
    public QuestionEntity getQuestionByLevel(Long level) {
        QuestionEntity entity = this.findByQuestionByLevel(level);

        List<AnswerEntity> answers = entity.getAnswers();

        List<AnswerEntity> activateAnswers = answers.stream().filter(AnswerEntity::getIsActivate).toList();

        /*todo : AI'a sor => Veriler db de yer alıyor, dışarıdan yanlış bir bilgi eklenmesi söz konus olmaza (@Valid).
        *  DBden gelen bilgileri yine de kontrol etmek gerekir mi ? Alttaki gibi kontrol sağlamak gerekir mi ?  */
        List<AnswerEntity> correctAnswer = activateAnswers.stream().filter(AnswerEntity::getIsCorrect)
                .findFirst()
                .map(List::of)
                //todo : new exception
                .orElseThrow(() -> new NotFoundException("There is no correct answer in question with level: " + level));

        List<AnswerEntity> wrongAnswers = activateAnswers.stream()
                .filter(answer -> !answer.getIsCorrect())
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                            if (list.size() < WRONG_ANSWER_LIMIT) {
                                new AnswerException("There is not enough wrong answer in question with questionId: " + entity.getId());
                            }
                            Collections.shuffle(list);
                            return list.stream().limit(WRONG_ANSWER_LIMIT).collect(Collectors.toList());
                        }
                ));

        List finalAnswers = new ArrayList();
        finalAnswers.addAll(correctAnswer);
        finalAnswers.addAll(wrongAnswers);

        entity.setAnswers(finalAnswers);

        return entity;
    }

    @Override
    public AnswerEntity handleAnswer(Long questionId, Long answerId) {

        return answerRepository.findByIdAndQuestionIdAndIsActivate(answerId, questionId, true)
                .orElseThrow(() -> new NotFoundException("Answer not found with questionId :" + questionId + " and answerId: " + answerId));
    }
}
