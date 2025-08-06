package org.maoco.milyoner.gameplay.service;

import org.maoco.milyoner.gameplay.controller.request.GameQuestionAnswerRequest;
import org.maoco.milyoner.gameplay.controller.request.GameQuestionQueryRequest;
import org.maoco.milyoner.gameplay.controller.request.StartGameRequest;
import org.maoco.milyoner.gameplay.controller.response.GameResponse;
import org.maoco.milyoner.gameplay.model.Game;
import org.maoco.milyoner.gameplay.model.Question;

public interface GameService {
    Question getQuestion(GameQuestionQueryRequest request);

    GameResponse checkAnswer(GameQuestionAnswerRequest request);

    Game startGame(StartGameRequest request);
}
