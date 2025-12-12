package org.maoco.milyoner.question.web.dto.request;

import lombok.Getter;

@Getter
public class QuestionQueryRequest {

    private Long questionLevel;
    private QuestionSorter sorter;

    private int pageNumber = 0;
    private int pageSize = 20;

    private enum QuestionSorter {
        TEXT("questionText"),
        LEVEL("questionLevel");

        final String name;

        QuestionSorter(String name) {
            this.name = name;
        }
    }

}
