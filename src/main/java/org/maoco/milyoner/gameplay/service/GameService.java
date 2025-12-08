package org.maoco.milyoner.gameplay.service;

import jakarta.validation.Valid;
import org.maoco.milyoner.common.error.GamePlayError;
import org.maoco.milyoner.common.exception.MilyonerException;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.common.security.GameAuthenticationToken;
import org.maoco.milyoner.common.security.GameSessionContext;
import org.maoco.milyoner.common.security.HashUtil;
import org.maoco.milyoner.common.security.TokenService;
import org.maoco.milyoner.gameplay.data.entity.GameEntity;
import org.maoco.milyoner.gameplay.domain.Answer;
import org.maoco.milyoner.gameplay.domain.Game;
import org.maoco.milyoner.gameplay.domain.Question;
import org.maoco.milyoner.gameplay.domain.UserScore;
import org.maoco.milyoner.gameplay.web.dto.request.GameQuestionAnswerRequest;
import org.maoco.milyoner.gameplay.web.dto.request.StartGameRequest;
import org.maoco.milyoner.question.data.entity.AnswerEntity;
import org.maoco.milyoner.question.service.QuestionQueryService;
import org.maoco.milyoner.question.web.controller.port_in.service.InsQuestionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final InsQuestionService insQuestionService;

    private final int WRONG_ANSWER_LIMITS = 3;
    private final QuestionQueryService questionQueryService;
    private final GamePersistenceService gamePersistenceService;
    private final HashUtil hashUtil;

    @Value("${jwt.secret}")
    private String secretKey;

    private final TokenService tokenService;

    public GameService(InsQuestionService insQuestionService, QuestionQueryService questionQueryService, GamePersistenceService gamePersistenceService, HashUtil hashUtil, TokenService tokenService) {
        this.insQuestionService = insQuestionService;
        this.questionQueryService = questionQueryService;
        this.gamePersistenceService = gamePersistenceService;
        this.hashUtil = hashUtil;
        this.tokenService = tokenService;
    }

    public String startGame(@Valid StartGameRequest request) {
        String username = request.getUsername();
        String gameId = UUID.randomUUID() + "-" + username + "-" + System.currentTimeMillis();
//        todo: burada gameId sonrasında hash edilecek
//        String hashGameId = hashUtil.convertStringToHash(gameId + secretKey);

        gamePersistenceService.createNewUser(new Game(
                gameId,
                GameState.START_GAME,
                0L,
                username
        ));

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("gameId", gameId);

        return tokenService.generateToken(username, extraClaims);
    }

    public Game getQuestions() {
        GameSessionContext currentSession = getCurrentSession();
        GameEntity gameEntity = checkUser(currentSession.gameId());
        var data = insQuestionService.getQuestion(gameEntity.getQuestionLevel());

        Question question = Question.of(data);
        List<Answer> answers = question.getAnswers();
        List<Answer> activateAnswers = answers.stream().filter(Answer::getIsActivate)
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        list -> {
                            if (list.size() < WRONG_ANSWER_LIMITS + 1) {
                                throw new NotFoundException("There is not enough answer in question with questionId: " + question.getQuestionId());
                            }
                            return list;
                        }));

        /*todo : AI'a sor => Veriler db de yer alıyor, dışarıdan yanlış bir bilgi eklenmesi söz konus olmaza (@Valid).
         *  DBden gelen bilgileri yine de kontrol etmek gerekir mi ? Alttaki gibi kontrol sağlamak gerekir mi ?  */
        List<Answer> correctAnswer = activateAnswers.stream().filter(Answer::getIsCorrect)
                .findFirst()
                .map(List::of)
                //todo : new exception
                .orElseThrow(() -> new NotFoundException("There is no enough correct answer in question questionId: " + question.getQuestionId()));

        List<Answer> wrongAnswers = activateAnswers.stream()
                .filter(answer -> !answer.getIsCorrect())
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                            if (list.size() < WRONG_ANSWER_LIMITS) {
                                new NotFoundException("There is not enough wrong answer in question with questionId: " + question.getQuestionId());
                            }
                            Collections.shuffle(list);
                            return list.stream().limit(WRONG_ANSWER_LIMITS).collect(Collectors.toList());
                        }
                ));

        List finalAnswers = new ArrayList();
        finalAnswers.addAll(correctAnswer);
        finalAnswers.addAll(wrongAnswers);
        Collections.shuffle(finalAnswers);
        question.setAnswers(finalAnswers);

        Game game = Game.buildGameFromGamerEntity(gameEntity);
        game.setQuestion(question);

        return game;
    }

    public Game checkAnswer(GameQuestionAnswerRequest request) {
        GameSessionContext session = getCurrentSession();
        GameEntity gameEntity = gamePersistenceService.findById(session.gameId());
        Boolean data = isAnswerCorrect(request.getAnswerId(), request.getQuestionId());

        if (gameEntity.getGameState() != GameState.IN_PROGRESS) {
            throw new MilyonerException(GamePlayError.INCORRECT_STATUS);
        }

        if (!data.equals(true)) {
            gameEntity.setGameState(GameState.LOST);
            GameEntity updatedGamer = gamePersistenceService.saveGamer(gameEntity);

            return Game.builder()
                    .questionLevel(null)
                    .gameState(updatedGamer.getGameState())
                    .build();
        }

        if (gameEntity.getQuestionLevel() == 10L) {
            gameEntity.setGameState(GameState.WON);

            GameEntity updatedGamer = gamePersistenceService.saveGamer(gameEntity);
            return Game.buildGameFromGamerEntity(updatedGamer);
        }

        gameEntity.setQuestionLevel(gameEntity.getQuestionLevel() + 1);
        GameEntity updatedGamer = gamePersistenceService.saveGamer(gameEntity);

        return Game.buildGameFromGamerEntity(updatedGamer);

    }

    private Boolean isAnswerCorrect(Long answerId, Long questionId) {
        AnswerEntity answerEntity = questionQueryService.handleAnswer(questionId, answerId);
        return answerEntity.getIsCorrect();
    }

    public UserScore getResult() {
        GameSessionContext session = getCurrentSession();
        GameEntity gameEntity = gamePersistenceService.findById(session.gameId());

        UserScore userScore = new UserScore(gameEntity.getUsername(), gameEntity.getQuestionLevel(), gameEntity.getGameState());

        if (gameEntity.getGameState() == GameState.WON) {
            userScore.setMessage("OYUNU KAZANDINIZ");
            return userScore;
        } else if (gameEntity.getGameState() == GameState.LOST) {
            userScore.setMessage("OYUNU KAYBETTİNİZ");
            return userScore;
        }

        //todo: kullanıcı oyundan mı çıktı ? Yanlis mi cevap verdi ? Bunun kontrolü eklenebilir
        throw new MilyonerException(GamePlayError.INCORRECT_STATUS);
    }

    private GameEntity checkUser(String id) {
        GameEntity gameEntity = gamePersistenceService.findById(id);

        if (gameEntity.getGameState() == GameState.START_GAME) {
            gameEntity.setGameState(GameState.IN_PROGRESS);
            gameEntity.setQuestionLevel(1L);
            return gamePersistenceService.saveGamer(gameEntity);
        }

        if (gameEntity.getGameState() != GameState.IN_PROGRESS) {
            throw new MilyonerException(GamePlayError.INCORRECT_STATUS);
        }

        return gameEntity;
    }

    private GameSessionContext getCurrentSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof GameAuthenticationToken gameAuth) {
            return gameAuth.getGameSessionContext();
        }
        throw new IllegalStateException("Güvenlik bağlamı bulunamadı veya geçersiz!");
    }
}
