INSERT INTO question (question_text, question_level, is_activate)
VALUES ('İnsanlar namaz kılarken nereye döner ?', 1, true);
INSERT INTO question (question_text, question_level, is_activate)
VALUES ('Allah kaç isme sahiptir', 2, true);
INSERT INTO question (question_text, question_level, is_activate)
VALUES ('Namazın içinde kaç tane farzı vardır ?', 3, true);

INSERT INTO answer (answer_text,is_activate,is_correct,question_id) VALUES ('Kabe',true,true,5);
INSERT INTO answer (answer_text,is_activate,is_correct,question_id) VALUES ('Mescid-i Aksa',true,false,5);
INSERT INTO answer (answer_text,is_activate,is_correct,question_id) VALUES ('Kubbetus Sahra',true,false,5);
INSERT INTO answer (answer_text,is_activate,is_correct,question_id) VALUES ('Mescidi Nebevi',true,false,5);

INSERT INTO answer (answer_text,is_activate,is_correct,question_id) VALUES ('999',true,false,2);
INSERT INTO answer (answer_text,is_activate,is_correct,question_id) VALUES ('9999',true,false,2);
INSERT INTO answer (answer_text,is_activate,is_correct,question_id) VALUES ('99',true,true,2);
INSERT INTO answer (answer_text,is_activate,is_correct,question_id) VALUES ('75',true,false,2);

INSERT INTO answer (answer_text,is_activate,is_correct,question_id) VALUES ('12',true,false,3);
INSERT INTO answer (answer_text,is_activate,is_correct,question_id) VALUES ('24',true,false,3);
INSERT INTO answer (answer_text,is_activate,is_correct,question_id) VALUES ('99',true,false,3);
INSERT INTO answer (answer_text,is_activate,is_correct,question_id) VALUES ('6',true,true,3);
