package org.maoco.milyoner.question.service.impl;

import lombok.RequiredArgsConstructor;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.question.entity.QuestionEntity;
import org.maoco.milyoner.question.repository.QuestionRepository;
import org.maoco.milyoner.question.service.QuestionQueryService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionQueryServiceImpl implements QuestionQueryService {

    private final QuestionRepository questionRepository;

    @Override
    public Collection<QuestionEntity> getAllQuestions() {
        /*return questionRepository.findAll();*/

        return List.of();
    }

    @Override
    public QuestionEntity getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NotFoundException("Question not found with id: " + id));
    }

    @Override
    public QuestionEntity getQuestionByLevel(Long level) {
        return questionRepository.findByQuestionLevel(level).orElseThrow(() -> new NotFoundException("Question not found with level: " + level));

    }
}
