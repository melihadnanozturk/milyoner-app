CREATE TABLE question
(
    id             SERIAL PRIMARY KEY,
    question_text  VARCHAR(255) NOT NULL,
    question_level INT          NOT NULL,
    is_activate    BOOLEAN      NOT NULL
);

CREATE TABLE answer
(
    id          SERIAL PRIMARY KEY,
    answer_text VARCHAR(255) NOT NULL,
    is_activate BOOLEAN      NOT NULL,
    is_correct  BOOLEAN      NOT NULL,
    question_id INT          not null references question (id) on delete cascade
)