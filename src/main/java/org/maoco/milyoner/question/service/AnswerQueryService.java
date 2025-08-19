package org.maoco.milyoner.question.service;

import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.question.data.entity.AnswerEntity;
import org.maoco.milyoner.question.data.repository.AnswerRepository;
import org.maoco.milyoner.question.domain.Answer;
import org.springframework.stereotype.Service;

@Service
public class AnswerQueryService {

    private final AnswerRepository repository;

    public AnswerQueryService(AnswerRepository repository) {
        this.repository = repository;
    }

    public Answer getAnswerById(Long id) {
        AnswerEntity answerEntity = repository.findById(id).orElseThrow(NotFoundException::new);

        return Answer.of(answerEntity);
    }
}
