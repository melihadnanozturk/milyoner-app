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
);

CREATE TYPE game_state_type AS ENUM('START_GAME',
    'IN_PROGRESS',
    'WON',
    'QUIT',
    'LOST');

CREATE TABLE gamer
(
    id             varchar(255) PRIMARY KEY, --burası direkt olarak not null olur mu ? Belirtmek lazım mı ?
    username       VARCHAR(50)     NOT NULL,
    game_id        VARCHAR(255)    NOT NULL,
    question_level INT             NOT NULL,
    game_state     game_state_type NOT NULL
);