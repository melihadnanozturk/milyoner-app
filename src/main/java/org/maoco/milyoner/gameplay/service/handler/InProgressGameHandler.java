package org.maoco.milyoner.gameplay.service.handler;

import org.maoco.milyoner.common.exception.AnswerException;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.gameplay.domain.Answer;
import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.domain.GamePhase;
import org.maoco.milyoner.gameplay.domain.Question;
import org.maoco.milyoner.question.web.controller.port_in.service.InsQuestionService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InProgressGameHandler implements GameStateHandler {

    private final InsQuestionService insQuestionService;

    public InProgressGameHandler(InsQuestionService insQuestionService) {
        this.insQuestionService = insQuestionService;
    }

    private final int WRONG_ANSWER_LIMITS = 3;


    @Override
    public GamePhase getGameStatus() {
        return GamePhase.IN_PROGRESS;
    }

    @Override
    public Game execute(Game game) {
        var data = insQuestionService.getQuestion(game.getQuestionLevel());

        Question question = Question.of(data);

        List<Answer> answers = question.getAnswers();

        List<Answer> activateAnswers = answers.stream().filter(Answer::getIsActivate)
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        list-> {
                            if (list.size()< WRONG_ANSWER_LIMITS + 1) {
                                throw new AnswerException("There is not enough answer in question with questionId: " + question.getQuestionId());
                            }
                            return list;
                        }));

        /*todo : AI'a sor => Veriler db de yer alıyor, dışarıdan yanlış bir bilgi eklenmesi söz konus olmaza (@Valid).
         *  DBden gelen bilgileri yine de kontrol etmek gerekir mi ? Alttaki gibi kontrol sağlamak gerekir mi ?  */
        List<Answer> correctAnswer = activateAnswers.stream().filter(Answer::getIsCorrect)
                .findFirst()
                .map(List::of)
                //todo : new exception
                .orElseThrow(() -> new NotFoundException("There is no correct answer in question with level: " + game.getQuestionLevel()));

        List<Answer> wrongAnswers = activateAnswers.stream()
                .filter(answer -> !answer.getIsCorrect())
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                            if (list.size() < WRONG_ANSWER_LIMITS) {
                                new AnswerException("There is not enough wrong answer in question with questionId: " + question.getQuestionId());
                            }
                            Collections.shuffle(list);
                            return list.stream().limit(WRONG_ANSWER_LIMITS).collect(Collectors.toList());
                        }
                ));

        List finalAnswers = new ArrayList();
        finalAnswers.addAll(correctAnswer);
        finalAnswers.addAll(wrongAnswers);

        question.setAnswers(finalAnswers);

        game.setQuestion(question);

        return game;
    }
}
