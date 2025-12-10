CREATE TYPE admin_roles AS ENUM(
    'ADMIN',
    'READER');

CREATE TABLE admin_user
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL,
    pass     VARCHAR(255) NOT NULL,
    roles    admin_roles  NOT NULL
)